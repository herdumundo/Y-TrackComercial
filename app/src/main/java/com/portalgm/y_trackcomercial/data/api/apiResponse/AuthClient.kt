package com.portalgm.y_trackcomercial.data.api.apiResponse

import com.portalgm.y_trackcomercial.data.api.UsuarioResponse
 import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface AuthClient {
    @POST("/usuariosYemsys")
    @FormUrlEncoded
    suspend fun iniciarSesion(
        @Field("user") user: String,
        @Field("password") password: String,
        @Field("app") app: String
    )   : Response<UsuarioResponse>
}

