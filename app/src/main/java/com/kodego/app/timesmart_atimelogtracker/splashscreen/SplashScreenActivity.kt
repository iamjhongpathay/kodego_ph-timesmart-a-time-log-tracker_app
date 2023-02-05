package com.kodego.app.timesmart_atimelogtracker.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.kodego.app.timesmart_atimelogtracker.MainActivity
import com.kodego.app.timesmart_atimelogtracker.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_splash_screen)


        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch{
            delay(3000L)
            startActivity(Intent( this@SplashScreenActivity , MainActivity::class.java))
            finish()
        }

    }
}