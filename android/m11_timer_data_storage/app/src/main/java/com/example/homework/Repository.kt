package com.example.homework

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Repository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    private var localValue: String? = null

    companion object {
        private const val PREFERENCE_NAME = "preference_name"
        private const val SHARED_PREFS_KEY = "shared_prefs_key"
    }

    fun saveText(text: String) {
        localValue = text
        prefs.edit().putString(SHARED_PREFS_KEY, text).apply()
    }

    fun getText(): String {
        val localData = getDataFromLocalVariable()
        val sharedData = getDataFromSharedPreference()

        return localData ?: sharedData ?: ""
    }

    private fun getDataFromSharedPreference(): String? {
        return prefs.getString(SHARED_PREFS_KEY, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return localValue
    }

    fun clearText() {
        prefs.edit().remove(SHARED_PREFS_KEY).apply()
        localValue = null
    }

}