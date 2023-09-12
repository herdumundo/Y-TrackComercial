package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.UbicacionesPvEntity
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UbicacionesPVClient {
    @POST("/ubicacionPv") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getUbicacionesPv(@Header("Authorization") authorization: String): List<UbicacionesPvEntity>
 }