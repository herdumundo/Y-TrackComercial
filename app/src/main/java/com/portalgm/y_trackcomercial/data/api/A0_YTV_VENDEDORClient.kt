package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.api.response.ApiResponse
 import com.portalgm.y_trackcomercial.data.api.response.ApiResponseLimiteCreditoNroFactura
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton

interface A0_YTV_VENDEDORClient {

    @POST("/vimar/ventas/A0_YTV_VENDEDOR_BY_ID") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getVENDEDOR(@Header("Authorization") authorization: String): List<A0_YTV_VENDEDOR_Entity>

    @POST("/vimar/ventas/UPDATE_NRO_FACTURA")
    suspend fun uploadData(@Body nroFacturaPendiente: nroFacturaPendiente, @Header("Authorization") authorization: String): ApiResponse

    @POST("/vimar/ventas/GET_NRO_FACTURA")
    suspend fun getUltimoNroFactura(@Body nroFacturaPendiente: nroFacturaPendiente, @Header("Authorization") authorization: String):  ApiResponseLimiteCreditoNroFactura

}