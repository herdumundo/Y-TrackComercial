package com.example.y_trackcomercial.data.api

 import com.example.y_trackcomercial.data.model.entities.LotesListasEntity
 import retrofit2.http.GET
 import javax.inject.Singleton
@Singleton
interface LotesListasApiClient {
    @GET("/LotesListas") // Reemplaza "tu/ruta/api" por la ruta correcta de tu API
    suspend fun getListasLotes(): List<com.example.y_trackcomercial.data.model.entities.LotesListasEntity>
}



