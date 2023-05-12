package com.example.y_trackcomercial.ui.login2.data.network

import com.example.y_trackcomercial.ui.login2.data.network.response.Usuarios
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(private val AuthClient: AuthClient) {
    suspend fun iniciarSesionServices(user: String, password: String): List<Usuarios> {
        return withContext(Dispatchers.IO) {
            val response = AuthClient.iniciarSesion()
            response.body()?.usuarios ?: emptyList()
        }
    }
}
