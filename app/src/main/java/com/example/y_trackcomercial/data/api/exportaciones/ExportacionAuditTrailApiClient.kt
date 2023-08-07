package com.example.y_trackcomercial.data.api.exportaciones

import com.example.y_trackcomercial.data.api.response.ApiResponse
import com.example.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditTrailApiClient {
    @POST("/insertarAllAuditoriaTrail")
    suspend fun uploadAuditoriaTrailData(@Body lotesAuditTrail: EnviarAuditoriaTrailRequest): ApiResponse
}