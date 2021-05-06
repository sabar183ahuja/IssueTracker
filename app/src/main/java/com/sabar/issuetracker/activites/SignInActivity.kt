package com.sabar.issuetracker.activites

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sabar.issuetracker.R
import com.sabar.issuetracker.firebase.FirestoreClass
import com.sabar.issuetracker.model.User
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        setupActionBar()

        btn_sign_in.setOnClickListener {
            signInRegisteredUser()
        }
    }


    private fun setupActionBar() {

        setSupportActionBar(toolbar_sign_in_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }

    private fun signInRegisteredUser() {
        // Here we get the text from editText and trim the space
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Sign-In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Calling the FirestoreClass  function to get the data of user from database.
                        FirestoreClass().loadUserData(this)
                        // END
                    } else {
                        Toast.makeText(
                            this,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    }
                }
        }

    fun signInSuccess(user: User) {

        hideProgressDialog()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
