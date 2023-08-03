package com.example.y_trackcomercial.data.network.exportaciones


import com.example.y_trackcomercial.model.models.ApiResponse
import com.example.y_trackcomercial.model.models.EnviarVisitasRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionVisitasApiService {
    @POST("/exportarVisitas")
    suspend fun uploadVisitasData(@Body lotesVisitas: EnviarVisitasRequest):ApiResponse
}