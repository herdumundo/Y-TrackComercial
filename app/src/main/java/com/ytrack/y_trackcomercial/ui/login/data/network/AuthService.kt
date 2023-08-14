package com.ytrack.y_trackcomercial.ui.login2.data.network

import UsuarioResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
/*
class AuthService @Inject constructor(private val AuthClient: AuthClient) {
    suspend fun iniciarSesionServices(user: String, password: String): List<Usuarios> {

        return withContext(Dispatchers.IO) {
            val response = AuthClient.iniciarSesion(user, password)
            response.body()?.usuarios ?: emptyList()
        }
    }
}*/

class AuthService @Inject constructor(private val authClient: AuthClient) {
    suspend fun iniciarSesionServices(user: String, password: String): UsuarioResponse? {
        return withContext(Dispatchers.IO) {
            val response = authClient.iniciarSesion(user, password,"ANDROID")
            response.body()
        }
    }
}