package com.example.y_trackcomercial.data.api.exportaciones


import com.example.y_trackcomercial.data.api.response.ApiResponse
import com.example.y_trackcomercial.data.api.request.EnviarVisitasRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionVisitasApiService {
    @POST("/exportarVisitas")
    suspend fun uploadVisitasData(@Body lotesVisitas: EnviarVisitasRequest): ApiResponse
}