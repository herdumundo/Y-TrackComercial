package com.ytrack.y_trackcomercial.data.api.exportaciones

import com.ytrack.y_trackcomercial.data.api.response.ApiResponse
import com.ytrack.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditTrailApiClient {
    @POST("/insertarAllAuditoriaTrail")
    suspend fun uploadAuditoriaTrailData(@Body lotesAuditTrail: EnviarAuditoriaTrailRequest): ApiResponse
}