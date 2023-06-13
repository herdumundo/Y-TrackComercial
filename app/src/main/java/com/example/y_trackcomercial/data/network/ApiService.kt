package com.example.y_trackcomercial.data.network

 import com.example.y_trackcomercial.data.network.response.UsuarioResponse
 import retrofit2.http.GET

interface ApiService {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}