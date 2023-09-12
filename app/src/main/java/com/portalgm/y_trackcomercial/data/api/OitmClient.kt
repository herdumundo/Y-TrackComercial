package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.OitmEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton

interface OitmClient {

    @POST("/OITM") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getOITM(@Header("Authorization") authorization: String): List<OitmEntity>
 }