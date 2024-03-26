package com.rubens.conectamedicina.data.auth

import android.content.SharedPreferences

class AuthTokenManager(
    private val prefs: SharedPreferences
) {
    fun getToken(): String?{
        return prefs.getString("jwt", null)
    }

    fun saveNewToken(token: String) {
        prefs.edit()
            .putString("jwt", token)
            .apply()


    }

    fun saveNewUsername(username: String) {
        prefs.edit()
            .putString("username", username)
            .apply()

    }
}