package com.example.y_trackcomercial.ui.login2.data.network

import com.example.y_trackcomercial.ui.login2.data.network.response.AuthResponse
import retrofit2.Response
 import retrofit2.http.POST

interface AuthClient {
    @POST("/usuariosYemsys")
    suspend fun iniciarSesion(): Response<AuthResponse>
}