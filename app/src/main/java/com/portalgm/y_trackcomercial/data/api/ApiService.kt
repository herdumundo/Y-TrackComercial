package com.portalgm.y_trackcomercial.data.api

import retrofit2.http.GET

interface ApiService {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}