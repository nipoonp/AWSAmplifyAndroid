package com.example.fudosample.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fudosample.R
import android.util.Log
import com.amazonaws.mobile.auth.userpools.SignUpActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignUpResult
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.HashMap


class SignUpActivity : AppCompatActivity() {

    private val TAG = SignUpActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        sign_up_button.setOnClickListener {
            Toast.makeText(this, "Sign up button clicked!", Toast.LENGTH_SHORT).show()
            onSignUp()
        }

    }

    private fun onSignUp() {
        val email = email.text.toString()
        val password = password.text.toString()

        val attributes: MutableMap<String, String> =
            HashMap()
        attributes["email"] = email
        AWSMobileClient.getInstance().signUp(
            email,
            password,
            attributes,
            null,
            object :
                Callback<SignUpResult> {
                override fun onResult(signUpResult: SignUpResult) {
                    runOnUiThread {
                        Log.d(
                            TAG,
                            "Sign-up callback state: " + signUpResult.confirmationState
                        )
                        if (!signUpResult.confirmationState) {
                            val details =
                                signUpResult.userCodeDeliveryDetails
                            Toast.makeText(this@SignUpActivity,"Confirm sign-up with: " + details.destination,Toast.LENGTH_SHORT)
                            val intent = Intent(this@SignUpActivity, SignUpConfirmActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity,"Sign-up done.",Toast.LENGTH_SHORT)

                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, "Sign-up error", e)
                }
            })
    }
}
