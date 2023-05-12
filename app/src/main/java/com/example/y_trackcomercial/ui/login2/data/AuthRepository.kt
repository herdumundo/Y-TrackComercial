package com.example.y_trackcomercial.ui.login2.data

import com.example.y_trackcomercial.ui.login2.data.network.AuthService
import com.example.y_trackcomercial.ui.login2.data.network.response.Usuarios
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthService){

    suspend fun iniciarSesionRepository(user:String, password:String): List<Usuarios>? {
        return try {
            api.iniciarSesionServices(user, password)
        } catch (e: Exception) {
            null
        }
    }
}
