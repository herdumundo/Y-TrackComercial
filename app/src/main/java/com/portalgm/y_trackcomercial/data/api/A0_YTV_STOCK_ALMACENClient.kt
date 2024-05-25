package com.portalgm.y_trackcomercial.data.api

import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_STOCK_ALMACEN_Entity
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface A0_YTV_STOCK_ALMACENClient {
    @POST("/vimar/ventas/A0_YTV_STOCK_ALMACEN")
    suspend fun getDatos(@Header("Authorization") authorization: String): List<A0_YTV_STOCK_ALMACEN_Entity>
 }