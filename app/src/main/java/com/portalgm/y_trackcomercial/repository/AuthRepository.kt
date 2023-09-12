package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.UsuarioResponse
import com.portalgm.y_trackcomercial.data.api.AuthService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthService){
    suspend fun iniciarSesionRepository(user:String, password:String): UsuarioResponse? {
        return try {
            api.iniciarSesionServices(user, password)

        } catch (e: Exception)
        {
            null
        }
    }
}