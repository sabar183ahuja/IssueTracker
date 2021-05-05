package com.sabar.issuetracker.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sabar.issuetracker.R
import com.sabar.issuetracker.firebase.FirestoreClass

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
            // Check if the current user id is not blank then send the user to MainActivity as he have logged in

            // Get the current user id
            val currentUserID = FirestoreClass().getCurrentUserID()

            if (currentUserID.isNotEmpty()) {
                Log.d(TAG,"User is already logged in!")
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Log.d(TAG,"Taking user to introduction page!")
                startActivity(Intent(this, IntroductionActivity::class.java))
            }
            finish()
        },2000)

    }

    companion object{
        const val TAG ="Splash Activity"
    }
}