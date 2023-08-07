package com.example.y_trackcomercial.data.api.exportaciones

import com.example.y_trackcomercial.data.api.response.ApiResponse
import com.example.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditLogApiClient {
    @POST("/insertarAllActivityLog")
    suspend fun uploadAuditLoglData(@Body lotesDeActividades: EnviarLotesDeActividadesRequest): ApiResponse
}