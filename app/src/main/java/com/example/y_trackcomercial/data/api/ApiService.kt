package com.example.y_trackcomercial.data.api

import com.example.y_trackcomercial.data.api.response.UsuarioResponse
 import retrofit2.http.GET

interface ApiService {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}