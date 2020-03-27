package com.example.fudosample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amazonaws.mobile.client.AWSMobileClient
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log_out_button.setOnClickListener {
            AWSMobileClient.getInstance().signOut()

            val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
