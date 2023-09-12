package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.OcrdOitmEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton

interface OcrdOitmClient {

    @POST("/ocrditem") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getOcrditem(@Header("Authorization") authorization: String): List<OcrdOitmEntity>
 }