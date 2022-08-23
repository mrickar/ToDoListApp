package com.example.todomvc.model

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CurrentUser {
    companion object{
        var auth: FirebaseAuth? = null
        var user:FirebaseUser? = null
        var uid:String? = null
        fun assignVariables()
        {
            user= auth!!.currentUser
            uid= auth!!.uid
        }

        fun signOut()
        {
            FirebaseAuth.getInstance().signOut()
            auth=null
            user=null
            uid=null
        }
    }
}