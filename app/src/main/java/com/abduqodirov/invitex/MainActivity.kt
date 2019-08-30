package com.abduqodirov.invitex

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.intro_navhostfragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = application.getSharedPreferences(getString(R.string.first_launch_key), Context.MODE_PRIVATE)

        val isFirstLaunch = sharedPref.getBoolean(getString(R.string.first_launch_key), true)

        if (!isFirstLaunch) {
            intro_navhostfragment.onDestroy()
        }
    }
}
