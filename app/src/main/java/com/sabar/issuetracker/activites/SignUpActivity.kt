package com.sabar.issuetracker.activites

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sabar.issuetracker.R
import com.sabar.issuetracker.firebase.FirestoreClass
import com.sabar.issuetracker.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.*
//import kotlinx.coroutines.Dispatchers.IO
//import kotlinx.coroutines.tasks.await

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // @TODO update depreciated method
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        btn_sign_up.setOnClickListener{
            registerUser()
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(){
        val name: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString()

        if (validateForm(name, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))

            /*
            Alternate implemnetation with coroutines
             */
//            signUpJob(name,email, password)

            // Recommended implementation for firebase

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        // If the registration is successfully done
                        if (task.isSuccessful) {
                            if (task.isSuccessful) {

                                // Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                // Registered Email
                                val registeredEmail = firebaseUser.email!!

                                val user = User(
                                    firebaseUser.uid, name, registeredEmail
                                )

                                // call the registerUser function of FirestoreClass to make an entry in the database.
                                FirestoreClass().registerUser(this, user)

                            } else {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }
    }
// co-routine suspend functions
//    private fun signUpJob(name:String, email:String,password: String ){
//
//        CoroutineScope ( IO ).launch{
//               val result: Deferred<AuthResult?> = async {
//                   signUpWithEmail( FirebaseAuth.getInstance(),email,password,name)
//               }
//               withContext(Dispatchers.Main){
//                   hideProgressDialog()
//               }
//               if (result.await() != null) {
////                    Firebase registered user
//                        val firebaseUser: FirebaseUser = result.await()!!.user!!
//                        // Registered Email
//                        val registeredEmail = firebaseUser.email!!
//                   FirebaseAuth.getInstance().signOut()
//                   finish()
//               }
//           }
//    }
//    private suspend fun signUpWithEmail(firebaseAuth: FirebaseAuth,
//                                        email:String, password:String, name: String
//    ): AuthResult? {
//        return try{
//            val data = firebaseAuth
//                .createUserWithEmailAndPassword(email,password)
//                .await()
//            return data
//        }catch (e : Exception){
//            return null
//        }
//    }

    fun userRegisteredSuccess() {

        Toast.makeText(
            this,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        // Finish the Sign-Up Screen
        finish()
    }
}