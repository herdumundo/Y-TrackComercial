package com.ytrack.y_trackcomercial.data.api.exportaciones

import com.ytrack.y_trackcomercial.data.api.response.ApiResponse
import com.ytrack.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionMovimientosApiClient {
    @POST("/insertarAllmovimientos")
    suspend fun uploadMovimientosData(@Body lotesDemovimientos: EnviarLotesDeMovimientosRequest): ApiResponse
}