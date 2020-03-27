package com.example.fudosample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserState
import com.amazonaws.mobile.client.UserStateDetails
import com.example.fudosample.auth.SignUpActivity
import kotlinx.android.synthetic.main.activity_authentication.*

class AuthenticationActivity : AppCompatActivity() {

    private val TAG = AuthenticationActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        signup_button_button.setOnClickListener {
            onSignUp()
        }


        AWSMobileClient.getInstance().initialize(
            applicationContext,
            object : Callback<UserStateDetails> {
                override fun onResult(userStateDetails: UserStateDetails) {
                    when (userStateDetails.userState) {
                        UserState.SIGNED_IN -> runOnUiThread {
                            text.text = "Logged IN"
                        }
                        UserState.SIGNED_OUT -> runOnUiThread {
                            text.text = "Logged OUT"
//                            showSignIn()
                        }
                        else -> AWSMobileClient.getInstance().signOut()
                    }
                }

                override fun onError(e: Exception) {
                    Log.e(TAG, e.toString())
                }
            })
    }

    private fun onSignUp () {
        val intent = Intent(this@AuthenticationActivity, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }



//    private fun showSignIn() {
//        try {
//            AWSMobileClient.getInstance().showSignIn(
//                this,
//                SignInUIOptions.builder().nextActivity(MainActivity::class.java).build()
//            )
//        } catch (e: Exception) {
//            Log.e(TAG, e.toString())
//        }
//    }
}

