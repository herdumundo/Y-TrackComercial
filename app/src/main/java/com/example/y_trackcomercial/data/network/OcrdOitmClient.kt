package com.example.y_trackcomercial.data.network

import com.example.y_trackcomercial.model.entities.OcrdOitmEntity
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton

interface OcrdOitmClient {

    @GET("/ocrditem") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getOcrditem(): List<OcrdOitmEntity>
 }