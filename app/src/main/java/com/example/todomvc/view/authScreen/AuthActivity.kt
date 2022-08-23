package com.example.todomvc.view.authScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todomvc.R
import com.example.todomvc.model.CurrentUser
import com.example.todomvc.view.mainScreen.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        CurrentUser.auth=FirebaseAuth.getInstance()
    }

}