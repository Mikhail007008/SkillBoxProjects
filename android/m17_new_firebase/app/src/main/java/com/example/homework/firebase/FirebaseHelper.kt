package com.example.homework.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class FirebaseHelper {
    fun getToken(onTokenReceived: (String) -> Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Registration token failed", task.exception)
            }

            val token = task.result
            onTokenReceived(token)
        }
    }
}