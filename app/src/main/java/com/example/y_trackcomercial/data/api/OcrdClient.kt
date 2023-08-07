package com.example.y_trackcomercial.data.api


import com.example.y_trackcomercial.data.model.entities.OCRDEntity
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton

interface OcrdClient {

    @GET("/OCRD") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getCustomers(): List<com.example.y_trackcomercial.data.model.entities.OCRDEntity>
 }