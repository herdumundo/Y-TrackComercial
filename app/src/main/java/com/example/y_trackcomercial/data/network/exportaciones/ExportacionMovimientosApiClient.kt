package com.example.y_trackcomercial.data.network.exportaciones

import com.example.y_trackcomercial.model.models.ApiResponse
import com.example.y_trackcomercial.model.models.EnviarAuditoriaTrailRequest
import com.example.y_trackcomercial.model.models.EnviarLotesDeMovimientosRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionMovimientosApiClient {
    @POST("/insertarAllmovimientos")
    suspend fun uploadMovimientosData(@Body lotesDemovimientos: EnviarLotesDeMovimientosRequest):ApiResponse
}