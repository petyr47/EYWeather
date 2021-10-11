package com.aneke.peter.eyweather.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.aneke.peter.eyweather.main.MainActivity
import com.aneke.peter.eyweather.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity( Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}