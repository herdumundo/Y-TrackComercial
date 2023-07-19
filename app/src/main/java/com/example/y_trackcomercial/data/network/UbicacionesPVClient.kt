package com.example.y_trackcomercial.data.network

import com.example.y_trackcomercial.model.entities.UbicacionesPvEntity
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UbicacionesPVClient {
    @POST("/ubicacionPv") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getUbicacionesPv(): List<UbicacionesPvEntity>
 }