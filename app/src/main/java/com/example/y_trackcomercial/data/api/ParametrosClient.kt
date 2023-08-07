package com.example.y_trackcomercial.data.api

import com.example.y_trackcomercial.data.model.entities.ParametrosEntity
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ParametrosClient {

    @GET("/OCRD") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getParametros(): List<com.example.y_trackcomercial.data.model.entities.ParametrosEntity>
 }