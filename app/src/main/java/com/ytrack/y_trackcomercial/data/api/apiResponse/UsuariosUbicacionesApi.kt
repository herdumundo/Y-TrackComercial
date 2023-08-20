package com.ytrack.y_trackcomercial.data.api.apiResponse

import com.ytrack.y_trackcomercial.data.api.response.UsuarioResponse
 import retrofit2.http.GET

interface UsuariosUbicacionesApi {
    @GET("ImportUsuarios")
    suspend fun getUsuarios(): List<UsuarioResponse>
}