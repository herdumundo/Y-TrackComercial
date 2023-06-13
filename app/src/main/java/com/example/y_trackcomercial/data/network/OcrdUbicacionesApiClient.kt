package com.example.y_trackcomercial.data.network

 import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity
 import retrofit2.http.GET
 import javax.inject.Singleton
@Singleton
interface OcrdUbicacionesApiClient {
    @GET("/OCRD_UBICACIONES")
    suspend fun getOcrdUbicaciones(): List<OcrdUbicacionEntity>
}



