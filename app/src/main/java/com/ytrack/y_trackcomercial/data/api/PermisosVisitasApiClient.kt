package com.ytrack.y_trackcomercial.data.api

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
    )   : List<com.ytrack.y_trackcomercial.data.model.entities.PermisosVisitasEntity>

}




