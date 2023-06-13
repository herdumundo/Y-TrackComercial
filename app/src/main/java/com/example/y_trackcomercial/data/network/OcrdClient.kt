package com.example.y_trackcomercial.data.network


import com.example.y_trackcomercial.model.entities.OCRDEntity
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton

interface OcrdClient {

    @GET("/OCRD") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getCustomers(): List<OCRDEntity>
 }