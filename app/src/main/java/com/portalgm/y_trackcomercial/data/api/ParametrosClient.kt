package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.ParametrosEntity
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ParametrosClient {

    @POST("/parametrosAndroid") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getParametros(@Header("Authorization") authorization: String): List<ParametrosEntity>

}