package com.example.y_trackcomercial.data.network.exportaciones

import com.example.y_trackcomercial.model.models.ApiResponse
import com.example.y_trackcomercial.model.models.EnviarAuditoriaTrailRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditTrailApiClient {
    @POST("/insertarAllAuditoriaTrail")
    suspend fun uploadAuditoriaTrailData(@Body lotesAuditTrail: EnviarAuditoriaTrailRequest):ApiResponse
}