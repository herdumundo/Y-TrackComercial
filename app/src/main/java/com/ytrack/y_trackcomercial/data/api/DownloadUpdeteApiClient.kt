package com.ytrack.y_trackcomercial.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import javax.inject.Singleton

@Singleton
interface DownloadUpdateApiClient {
    @GET("/apps")
    @Streaming // Indica que se descargará un archivo grande
    suspend fun downloadApk(): ResponseBody
}
