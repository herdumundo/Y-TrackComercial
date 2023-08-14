package com.ytrack.y_trackcomercial.data.api

import com.ytrack.y_trackcomercial.data.api.response.UsuarioResponse
 import retrofit2.http.GET

interface ApiService {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}