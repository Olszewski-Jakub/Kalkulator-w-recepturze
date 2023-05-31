package com.jakubolszewski.kalkulatorwrecepturzedoz

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.AlcoholConcentrationModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.AlcoholDegreeModel
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.DBHelper
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.ImportData
import com.jakubolszewski.kalkulatorwrecepturzedoz.database.VitAModel
import org.json.JSONArray
import org.json.JSONObject
import java.security.acl.Permission

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var dbHelper: DBHelper
    private var TAG: String = "MainActivity"
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Obtain the FirebaseAnalytics instance.
        //Initialize Firebase
        FirebaseApp.initializeApp(this)
        firebaseAnalytics = Firebase.analytics
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
                    ImportData(this).updateDataBase(constans)

                } else {
                    Log.d(TAG, "Fetch failed")
                }
            }
        navController.navigate(R.id.action_splashScreenFragment_to_homeFragment)
    }


}