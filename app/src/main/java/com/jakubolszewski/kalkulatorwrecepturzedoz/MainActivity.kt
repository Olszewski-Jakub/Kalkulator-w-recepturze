package com.jakubolszewski.kalkulatorwrecepturzedoz

import android.os.Bundle
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
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.AlcoholConcentrationModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.VitAModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.HomeFragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.SplashScreenFragment
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var dbHelper: DBHelper
    private var TAG: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Obtain the FirebaseAnalytics instance.
        //Initialize Firebase
        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, SplashScreenFragment.newInstance(), "Home")
                .commit()
        }
        // Initialize SQLite database
        dbHelper = DBHelper(this)


        fetch_and_save()
    }


    private fun fetch_and_save() {
        //Initialize Firebase Remote Config
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        //Fetch data from Firebase Remote Config
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                    Log.d(TAG, "Fetch and activate succeeded")

                    //Save boolean values according button states to SharedPreferences
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

                    //Save data from Firebase Remote Config to SQLite database
                    val constans: String = remoteConfig.getString("Constants")
                    Log.d(TAG, constans)
                    updateDataBase(constans)


                } else {
                    Log.d(TAG, "Fetch failed")
                }
            }
    }

    //Convert JSON String to Map
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

    //TODO: Database is unable to update records
    // "android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tbl_vit_a.id (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
    private fun updateDataBase(jsonString: String) {
        //Convert JSON String to Map
        val jsonObj = JSONObject(jsonString)
        val jsonMap = jsonObj.toMap()
        //Save data according vitamin A from Firebase Remote Config to SQLite database
        val vitaminAData: String = jsonMap.getValue("vit_A").toString()
        val vitaminAJsonObject = JSONObject(vitaminAData)
        val vitaminAMap = vitaminAJsonObject.toMap()
        //Crearte Map if different values of VitA
        convertVItA(
            JSONObject(vitaminAMap["hasco"].toString()).toMap(),
            JSONObject(vitaminAMap["medana"].toString()).toMap(),
            JSONObject(vitaminAMap["fagron"].toString()).toMap()
        )

        //Save data according Alcohol from Firebase Remote Config to SQLite database
        val alcoholData = jsonMap.getValue("alkohol").toString()
        val alcoholJsonObject = JSONObject(alcoholData)
        val alcoholMap = alcoholJsonObject.toMap()
//        Log.d(TAG, JSONObject(.toString())
        convertAlcoholConcentration(JSONObject(alcoholMap["concentration"].toString()).toMap())
    }

    private fun convertVItA(
        hascoMap: Map<String, *>,
        medanaMap: Map<String, *>,
        fagronMap: Map<String, *>
    ) {
        //Create Map of VitA models to save in database
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
        //TODO Needs to be fixed. Implement isVitAEmpty() method it probably needs fixing.
        // Priority is not high app doesn't crash, throws an error in logcat
        //if (true){
        val hasco_status = dbHelper.insertVitA(hascoModel)
        val medana_status = dbHelper.insertVitA(medanaModel)
        val fagron_status = dbHelper.insertVitA(fagronModel)

        if (hasco_status > -1 && medana_status > -1 && fagron_status > -1) {
            //TODO() Remove before deployment
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }
        //}else {
        //Update data in database
        dbHelper.updateVitA(hascoModel)
        dbHelper.updateVitA(medanaModel)
        dbHelper.updateVitA(fagronModel)
        //}


        Toast.makeText(this, "Success Update", Toast.LENGTH_SHORT).show()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment.newInstance(), "Home")
            .commit()
    }

    private fun convertAlcoholConcentration(
        alcoholMap: Map<String, *>
    ) {
        Log.d(TAG, alcoholMap.toString())
        var check = dbHelper.isAlcoholConcentrationEmpty()
        if (!check) {
            var id: Int = 0
            for (x in alcoholMap) {
                Log.d(TAG, x.key.toString())
                val alcoholModel = AlcoholConcentrationModel(
                    id = id,
                    alcohol_concentration = x.key,
                    alcohol_volume = x.value.toString().toDouble()
                )
                id++
                dbHelper.insertAlcoholConcentration(alcoholModel)
            }
        }

    }
}

