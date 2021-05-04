package com.sabar.issuetracker

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat.postDelayed

class SplashActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
//        window.insetsController!!.hide(WindowInsets.Type.statusBars())


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,IntroductionActivity::class.java))
            finish()
        },3000)

    }
}