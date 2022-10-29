package com.jakubolszewski.kalkulatorwrecepturzedoz

import android.content.ContentValues.TAG
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
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.HomeFragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.fragments.SplashScreenFragment


class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

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
        if (fetch_and_save()){
            val mainLooperHandler = Handler(Looper.getMainLooper())

            mainLooperHandler.postDelayed(Runnable {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance(), "Home")
                    .commit()

            }, 2000)
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
                    Log.d(TAG, "Config params updated: $updated")
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
        val b_witamina_A: Boolean = remoteConfig.getBoolean("Witamina_A");
        val b_witamina_E: Boolean = remoteConfig.getBoolean("Witamina_E");
        val b_witamina_A_D3: Boolean = remoteConfig.getBoolean("Witamina_A_D3");
        val b_Devicap: Boolean = remoteConfig.getBoolean("Devicap");
        val b_Spirytus: Boolean = remoteConfig.getBoolean("Spirytus");
        val b_Olejki: Boolean = remoteConfig.getBoolean("Olejki");
        val b_Oleje: Boolean = remoteConfig.getBoolean("Oleje");
        val prefs = PreferenceManager.getDefaultSharedPreferences(this) // getActivity() for Fragment

        val witamina_A = prefs.edit().putBoolean("witamina_A", b_witamina_A).commit()
        val witamina_e = prefs.edit().putBoolean("witamina_E", b_witamina_E).commit()
        val witamina_A_D3 = prefs.edit().putBoolean("witamina_A_D3", b_witamina_A_D3).commit()
        val devicap = prefs.edit().putBoolean("devicap", b_Devicap).commit()
        val spirytus = prefs.edit().putBoolean("spirytus", b_Spirytus).commit()
        val oleje = prefs.edit().putBoolean("oleje", b_Olejki).commit()
        val olejki = prefs.edit().putBoolean("olejki", b_Oleje).commit()

        return true;
    }
}