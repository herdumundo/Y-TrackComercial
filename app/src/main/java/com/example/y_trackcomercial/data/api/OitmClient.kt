package com.example.y_trackcomercial.data.api

import com.example.y_trackcomercial.data.model.entities.OitmEntity
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton

interface OitmClient {

    @GET("/OITM") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getOITM(): List<com.example.y_trackcomercial.data.model.entities.OitmEntity>
 }