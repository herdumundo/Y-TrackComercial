package com.portalgm.y_trackcomercial.data.api.exportaciones


 import com.portalgm.y_trackcomercial.data.api.request.NuevoPass
 import com.portalgm.y_trackcomercial.data.api.response.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExportacionNewPassApiClient {
    @POST("/usuariosUpdatePass")
    suspend fun uploadData(@Body nuevoPass: NuevoPass,@Header("Authorization") authorization: String): ApiResponse
}