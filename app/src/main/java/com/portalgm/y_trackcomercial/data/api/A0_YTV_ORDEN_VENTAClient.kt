package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_ORDEN_VENTA_Entity
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface A0_YTV_ORDEN_VENTAClient {
    @POST("/vimar/ventas/A0_YTV_ORDEN_VENTA") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getDatos(@Header("Authorization") authorization: String): List<A0_YTV_ORDEN_VENTA_Entity>
 }