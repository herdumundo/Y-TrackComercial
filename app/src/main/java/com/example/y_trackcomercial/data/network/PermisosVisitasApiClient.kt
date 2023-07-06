package com.example.y_trackcomercial.data.network

import com.example.y_trackcomercial.model.entities.PermisosVisitasEntity
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface PermisosVisitasApiClient {
    @POST("/permisosVisitasSelect")
    @FormUrlEncoded
    suspend fun getPermisosVisitas(
        @Field("idUsuario") idUsuario: Int
    )   : List<PermisosVisitasEntity>

}




