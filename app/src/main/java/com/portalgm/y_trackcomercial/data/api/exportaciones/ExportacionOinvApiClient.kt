package com.portalgm.y_trackcomercial.data.api.exportaciones

import com.portalgm.y_trackcomercial.data.api.response.ApiResponse
import com.portalgm.y_trackcomercial.data.api.response.ApiResponseFacturacion
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.repository.ventasRepositories.ConfirmacionData
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionOinvApiClient {
    @POST("/vimar/ventas/INSERT_ALL_OINV")
    //suspend fun uploadData(@Body lotesDemovimientos:  LotesMovimientosOinv, @Header("Authorization") authorization: String): ApiResponse
    suspend fun uploadData(@Body lotesDemovimientos: DatosMovimientosOinv, @Header("Authorization") authorization: String): ApiResponseFacturacion


    @POST("/vimar/ventas/CONFIRMAR_FACTURAS")
     suspend fun confirmarFacturas(@Body confirmacionData: ConfirmacionData, @Header("Authorization") authorization: String): ApiResponse



}