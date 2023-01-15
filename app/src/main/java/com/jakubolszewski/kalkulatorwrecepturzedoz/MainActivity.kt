package com.jakubolszewski.kalkulatorwrecepturzedoz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.VitAModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.HomeFragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.SplashScreenFragment
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SplashScreenFragment.newInstance(), "Home")
                .commit()
        }

        dbHelper = DBHelper(this)
        fetch_and_save()

        val mainLooperHandler = Handler(Looper.getMainLooper())

        mainLooperHandler.postDelayed(Runnable {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(), "Home")
                .commit()

        }, 2000)


        val fragment = SplashScreenFragment()
        fragment.listener = {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance(), "Home")
                .commit()
        }
    }


    fun fetch_and_save(): Boolean {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("TAG", "Config params updated: $updated")
                    Toast.makeText(
                        this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Fetch failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        val constans: String = remoteConfig.getString("Constants")
        updateDataBase(constans)
        val b_witamina_A: Boolean = remoteConfig.getBoolean("Witamina_A");
        val b_witamina_E: Boolean = remoteConfig.getBoolean("Witamina_E");
        val b_witamina_A_D3: Boolean = remoteConfig.getBoolean("Witamina_A_D3");
        val b_Devicap: Boolean = remoteConfig.getBoolean("Devicap");
        val b_Spirytus: Boolean = remoteConfig.getBoolean("Spirytus");
        val b_Olejki: Boolean = remoteConfig.getBoolean("Olejki");
        val b_Oleje: Boolean = remoteConfig.getBoolean("Oleje");
        val prefs =
            PreferenceManager.getDefaultSharedPreferences(this) // getActivity() for Fragment

        prefs.edit().putBoolean("witamina_A", b_witamina_A).apply()
        prefs.edit().putBoolean("witamina_E", b_witamina_E).apply()
        prefs.edit().putBoolean("witamina_A_D3", b_witamina_A_D3).apply()
        prefs.edit().putBoolean("devicap", b_Devicap).apply()
        prefs.edit().putBoolean("spirytus", b_Spirytus).apply()
        prefs.edit().putBoolean("oleje", b_Olejki).apply()
        prefs.edit().putBoolean("olejki", b_Oleje).apply()

        return false;
    }

    fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
        when (val value = this[it]) {
            is JSONArray -> {
                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
                JSONObject(map).toMap().values.toList()
            }
            is JSONObject -> value.toMap()
            JSONObject.NULL -> null
            else -> value
        }
    }

    private fun updateDataBase(jsonString: String) {
        val jsonObj = JSONObject(jsonString)
        val jsonMap = jsonObj.toMap()

        var vitaminAData: String = jsonMap.getValue("vit_A").toString()
        val vitaminAJsonObject = JSONObject(vitaminAData)
        val vitaminAMap = vitaminAJsonObject.toMap()

        convertVItA(
            JSONObject(vitaminAMap["hasco"].toString()).toMap(),
            JSONObject(vitaminAMap["medana"].toString()).toMap(),
            JSONObject(vitaminAMap["fagron"].toString()).toMap()
        )

    }

    private fun convertVItA(
        hascoMap: Map<String, *>,
        medanaMap: Map<String, *>,
        fagronMap: Map<String, *>
    ) {
        val hascoModel = VitAModel(
            id = 0,
            company = "hasco",
            density = hascoMap["density"].toString().toDouble(),
            drops = hascoMap["drops"].toString().toInt(),
            mass_units = hascoMap["mass_units"].toString().toDouble()
        )
        val medanaModel = VitAModel(
            id = 1,
            company = "medana",
            density = medanaMap["density"].toString().toDouble(),
            drops = medanaMap["drops"].toString().toInt(),
            mass_units = medanaMap["mass_units"].toString().toDouble()
        )
        val fagronModel = VitAModel(
            id = 2,
            company = "fagron",
            density = fagronMap["density"].toString().toDouble(),
            drops = fagronMap["drops"].toString().toInt(),
            mass_units = fagronMap.get("mass_units").toString().toDouble()
        )

        val hasco_status = dbHelper.insertVitA(hascoModel)
        val medana_status = dbHelper.insertVitA(medanaModel)
        val fagron_status = dbHelper.insertVitA(fagronModel)
        if (hasco_status > -1 && medana_status > -1 && fagron_status > -1) {
            //TODO() Remove before deployment
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
    }

}

