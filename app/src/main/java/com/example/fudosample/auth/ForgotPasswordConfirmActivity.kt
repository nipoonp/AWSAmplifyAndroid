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
import kotlinx.android.synthetic.main.activity_forgot_password_confirm.*

class ForgotPasswordConfirmActivity : AppCompatActivity() {

    private val TAG = ForgotPasswordConfirmActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_confirm)

        forgot_password_confirm_button.setOnClickListener {
            Toast.makeText(this, "Sign up button clicked!", Toast.LENGTH_SHORT).show()
            onForgotPasswordConfirm()
        }
    }

    private fun onForgotPasswordConfirm() {
        val newPassword = new_password.text.toString()
        val confirmation_code = confirmation_code.text.toString()


        AWSMobileClient.getInstance().confirmForgotPassword(
            newPassword,
            confirmation_code,
            object :
                Callback<ForgotPasswordResult> {
                override fun onResult(result: ForgotPasswordResult) {
                    runOnUiThread {
                        Log.d(TAG, "forgot password state: " + result.state)
                        when (result.state) {
                            ForgotPasswordState.DONE -> {
                                onForgotPasswordConfirmComplete()
                            }
                            else -> Log.e(
                                TAG,
                                "un-supported forgot password state"
                            )
                        }
                    }
                }

                override fun onError(e: java.lang.Exception) {
                    Log.e(TAG, "forgot password error", e)
                }
            })
    }

    private fun onForgotPasswordConfirmComplete() {
        Toast.makeText(
            this@ForgotPasswordConfirmActivity,
            "Password changed successfully",
            Toast.LENGTH_SHORT
        ).show();

        val intent = Intent(this@ForgotPasswordConfirmActivity, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }
}
