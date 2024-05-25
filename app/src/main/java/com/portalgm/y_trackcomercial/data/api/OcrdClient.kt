package com.portalgm.y_trackcomercial.data.api


 import com.portalgm.y_trackcomercial.data.api.response.ApiResponse
 import com.portalgm.y_trackcomercial.data.api.response.ApiResponseOcrd
 import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
 import retrofit2.http.GET
 import retrofit2.http.Header
 import retrofit2.http.POST
 import javax.inject.Singleton

@Singleton

interface OcrdClient {


    @POST("/OCRD") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    // suspend fun getCustomers(@Header("Authorization") authorization: String): List<OCRDEntity>
    suspend fun getCustomers(@Header("Authorization") authorization: String): List<ApiResponseOcrd>
 }