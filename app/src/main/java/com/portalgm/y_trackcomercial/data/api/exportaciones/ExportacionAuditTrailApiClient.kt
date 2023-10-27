package com.portalgm.y_trackcomercial.data.api.exportaciones

import com.portalgm.y_trackcomercial.data.api.response.ApiResponse
import com.portalgm.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditTrailApiClient {
    @POST("/insertarAllAuditoriaTrail_V2")
    suspend fun uploadAuditoriaTrailData(@Body lotesAuditTrail: EnviarAuditoriaTrailRequest
                                         ,@Header("Authorization") authorization: String): ApiResponse
}