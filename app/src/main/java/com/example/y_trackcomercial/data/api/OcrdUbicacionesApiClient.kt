package com.example.y_trackcomercial.data.api

 import com.example.y_trackcomercial.data.model.entities.OcrdUbicacionEntity
 import retrofit2.http.GET
 import javax.inject.Singleton
@Singleton
interface OcrdUbicacionesApiClient {
    @GET("/OCRD_UBICACIONES")
    suspend fun getOcrdUbicaciones(): List<com.example.y_trackcomercial.data.model.entities.OcrdUbicacionEntity>
}



