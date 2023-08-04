package com.example.y_trackcomercial.data.network.exportaciones

import com.example.y_trackcomercial.model.models.ApiResponse
import com.example.y_trackcomercial.model.models.EnviarLotesDeActividadesRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionAuditLogApiClient {
    @POST("/insertarAllActivityLog")
    suspend fun uploadAuditLoglData(@Body lotesDeActividades: EnviarLotesDeActividadesRequest):ApiResponse
}