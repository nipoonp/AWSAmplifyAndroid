package com.example.fudosample.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amazonaws.mobile.auth.userpools.SignUpActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignInResult
import com.amazonaws.mobile.client.results.SignInState
import com.example.fudosample.MainActivity
import com.example.fudosample.R
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    private val TAG = SignUpActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        sign_up_button.setOnClickListener {
            onSignUp()
        }

        forgot_password_button.setOnClickListener {
            onForgotPassword()
        }

        log_in_button.setOnClickListener {
            onLogIn()
        }
    }

    private fun onLogIn() {
        val email = email.text.toString()
        val password = password.text.toString()

        AWSMobileClient.getInstance().signIn(
            email,
            password,
            null,
            object : Callback<SignInResult> {
                override fun onResult(signInResult: SignInResult) {
                    runOnUiThread {
                        Log.d(
                            TAG,
                            "Sign-in callback state: " + signInResult.signInState
                        )
                        when (signInResult.signInState) {
                            SignInState.DONE -> onLoggedin()
//                            SignInState.SMS_MFA -> // NOT USED
                            SignInState.NEW_PASSWORD_REQUIRED -> onNewPasswordRequired() // This is for admin created accounts
                            else -> Toast.makeText(this@LogInActivity, "Unsupported sign-in confirmation: " + signInResult.signInState, Toast.LENGTH_SHORT)
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, "Sign-in error", e)
                }
            })
    }


    private fun onForgotPassword () {
        val intent =
            Intent(this@LogInActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onSignUp () {
        val intent =
            Intent(this@LogInActivity, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun onLoggedin() {
        val intent = Intent(this@LogInActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onNewPasswordRequired() {
        Toast.makeText(this@LogInActivity, "New Password Required", Toast.LENGTH_LONG)
        val intent = Intent(this@LogInActivity, NewPasswordRequiredActivity::class.java)
        startActivity(intent)
        finish()
    }

}
