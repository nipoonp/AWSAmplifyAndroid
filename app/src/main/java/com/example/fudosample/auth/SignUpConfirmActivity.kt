package com.example.fudosample.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fudosample.R
import android.util.Log
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.results.SignUpResult
import com.example.fudosample.AuthenticationActivity
import kotlinx.android.synthetic.main.activity_sign_up_confirm.*



class SignUpConfirmActivity : AppCompatActivity() {

    private val TAG = SignUpConfirmActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_confirm)


        sign_up_confirm_button.setOnClickListener {
            onSignUpConfirm()
        }

    }

    private fun onSignUpConfirm() {
        val email = email.text.toString()
        val code = code.text.toString()

        AWSMobileClient.getInstance().confirmSignUp(
            email,
            code,
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
                            Toast.makeText(this@SignUpConfirmActivity,"Confirm sign-up with: ${details.destination}",Toast.LENGTH_SHORT)
                        } else {
                            Toast.makeText(this@SignUpConfirmActivity,"Sign up done.",Toast.LENGTH_SHORT)
                            val intent = Intent(this@SignUpConfirmActivity, AuthenticationActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, "Confirm sign-up error", e)
                }
            })
    }
}
