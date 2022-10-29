package com.jakubolszewski.kalkulatorwrecepturzedoz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakubolszewski.kalkulatorwrecepturzedoz.ui.main.MainFragment
import com.jakubolszewski.kalkulatorwrecepturzedoz.ui.main.VitaminAFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VitaminAFragment.newInstance())
                .commitNow()
        }
    }
}