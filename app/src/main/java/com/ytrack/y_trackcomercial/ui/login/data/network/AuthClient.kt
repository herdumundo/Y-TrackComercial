package com.ytrack.y_trackcomercial.ui.login2.data.network

import UsuarioResponse
 import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthClient {
    @POST("/usuariosYemsys")
    @FormUrlEncoded
    suspend fun iniciarSesion(
        @Field("user") user: String,
        @Field("password") password: String,
        @Field("app") app: String
    )   : Response<UsuarioResponse>
}

