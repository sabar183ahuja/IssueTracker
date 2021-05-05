package com.sabar.issuetracker.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sabar.issuetracker.R
import kotlinx.android.synthetic.main.activity_introduction.*

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        btn_sign_in_intro.setOnClickListener {
            // Launch the sign in screen.
            startActivity(Intent(this, SignInActivity::class.java))
        }

        btn_sign_up_intro.setOnClickListener {
            // Launch the sign up screen.
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}