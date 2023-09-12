package com.portalgm.y_trackcomercial.data.api.apiResponse

import com.portalgm.y_trackcomercial.data.api.response.ApiKeyGMSResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface ApikKeyGMSApi {
    @POST("ApikeyGMS")
    suspend fun getApikeyGMS(@Header("Authorization") authorization: String):ApiKeyGMSResponse
}