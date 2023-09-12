package com.portalgm.y_trackcomercial.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class TokenManager  @Inject constructor (private val context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)

    private val TOKEN_KEY = "token"

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }
}