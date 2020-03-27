package com.example.fudosample.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.ForgotPasswordResult
import com.amazonaws.mobile.client.results.ForgotPasswordState
import com.example.fudosample.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = ForgotPasswordActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        forgot_password_button.setOnClickListener {
            Toast.makeText(this, "Sign up button clicked!", Toast.LENGTH_SHORT).show()
            onForgotPassword()
        }
    }

    private fun onForgotPassword() {
        val email = email.text.toString()

        AWSMobileClient.getInstance().forgotPassword(
            email,
            object :
                Callback<ForgotPasswordResult> {
                override fun onResult(result: ForgotPasswordResult) {
                    runOnUiThread {
                        Log.d(TAG, "forgot password state: " + result.state)
                        when (result.state) {
                            ForgotPasswordState.CONFIRMATION_CODE -> {onForgotPasswordComplete()}
                            else -> Log.e(
                                TAG,
                                "un-supported forgot password state"
                            )
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, "forgot password error", e)
                }
            })
    }

    private fun onForgotPasswordComplete() {
        Toast.makeText(
            this@ForgotPasswordActivity,
            "Confirmation code is sent to reset password",
            Toast.LENGTH_SHORT
        )

        val intent = Intent(this@ForgotPasswordActivity, ForgotPasswordConfirmActivity::class.java)
        startActivity(intent)
        finish()
    }
}

