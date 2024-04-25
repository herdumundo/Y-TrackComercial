package com.portalgm.y_trackcomercial.util

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SiediPreferences @Inject constructor(private val context: Context) {

    private val siediPreferences: SharedPreferences =  context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val TXT_SIEDI = ""

     fun saveTxtSiedi(
        txtSiedi: String) {
        val editor = siediPreferences.edit()
        editor.putString(TXT_SIEDI,txtSiedi)
        editor.apply()
    }

    fun getTxtSiedi(): String? {
        return siediPreferences.getString(TXT_SIEDI, "")
    }



    fun clearPreferences() {
        siediPreferences.edit().clear().apply()
    }
}