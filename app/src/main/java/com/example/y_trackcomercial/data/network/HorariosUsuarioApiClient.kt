package com.example.y_trackcomercial.data.network

import com.example.y_trackcomercial.model.entities.HorariosUsuarioEntity
import com.example.y_trackcomercial.model.models.HorariosUsuarioResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface HorariosUsuarioApiClient {
    @POST("/horarioUsuario")
    @FormUrlEncoded
    suspend fun getHorarioUsuario(
        @Field("idUsuario") idUsuario: Int
    )   : List<HorariosUsuarioResponse>
}