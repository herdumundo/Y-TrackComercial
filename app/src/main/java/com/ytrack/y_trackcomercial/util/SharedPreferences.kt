package com.ytrack.y_trackcomercial.util

import RutasAccesos
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class SharedPreferences @Inject constructor(private val context: Context) {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val TOKEN_KEY = "token"
    private val ROL_USER = "rolUser"
    private val NAME_USER = "nameUser"
    private val PASS_USER = "passUser"
    private val RUTAS_ACCESOS = "rutasAccesos"
    private val USER_LOGIN = "userLogin"
    private val ÍD_USER = "userId"



    fun savePreferences(Rol: String, UserName: String, Token: String,PassUserName:String,UserLogin:String,UserID:Int, rutasAccesos: List<RutasAccesos>) {
        val editor = sharedPreferences.edit()
        editor.putString(ROL_USER, Rol)
        editor.putString(TOKEN_KEY, Token)
        editor.putString(NAME_USER, UserName)
        editor.putString(PASS_USER, PassUserName)
        editor.putString(USER_LOGIN, UserLogin)
        editor.putInt(ÍD_USER, UserID)

        // Convertir la lista de RutaAcceso a una cadena JSON
        val rutasAccesosJson = Gson().toJson(rutasAccesos)
        editor.putString(RUTAS_ACCESOS, rutasAccesosJson)
        editor.apply()
    }


    fun getUserName(): String? {
        return sharedPreferences.getString(NAME_USER, null)

    }

    fun getPasswordUserName(): String? {
        return sharedPreferences.getString(PASS_USER, null)

    }
    fun getUserLogin(): String? {
        return sharedPreferences.getString(USER_LOGIN, null)

    }
    fun getUserId(): Int {
        return sharedPreferences.getInt(ÍD_USER, 0)
    }

    fun getRol(): String? {
        return sharedPreferences.getString(ROL_USER, null)
    }

    fun getRutasAccesos():List<RutasAccesos>{
        val rutasAccesosJson = sharedPreferences.getString(RUTAS_ACCESOS, null)

        // Verificar si hay datos guardados en las preferencias compartidas
        if (rutasAccesosJson != null) {
            // Convertir la cadena JSON a una lista de objetos RutaAcceso
            val rutasAccesosType = object : TypeToken<List<RutasAccesos>>() {}.type
            return Gson().fromJson(rutasAccesosJson, rutasAccesosType)
        }

        // Si no hay datos guardados, retornar una lista vacía
        return emptyList()
    }



    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }


    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}