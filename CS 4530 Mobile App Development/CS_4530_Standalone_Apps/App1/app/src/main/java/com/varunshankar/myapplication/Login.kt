package com.varunshankar.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Login : AppCompatActivity() {

    private var mWelcome: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mWelcome = findViewById(R.id.textViewWelcome)

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")

        //Set the data
        if (firstName != null && lastName != null) {
            val sb = StringBuilder()
            sb.append(firstName).append(" ").append(lastName).append(" is logged in!")
            mWelcome!!.text = sb.toString()
        }
    }
}