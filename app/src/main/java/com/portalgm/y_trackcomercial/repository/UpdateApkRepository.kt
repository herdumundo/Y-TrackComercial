package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.DownloadUpdateApiClient
import okhttp3.ResponseBody
import javax.inject.Inject


class UpdateRepository @Inject constructor(private val apiClient: DownloadUpdateApiClient) : UpdateRepositoryInterface {
    override suspend fun downloadApk(): ResponseBody {
        return apiClient.downloadApk()
    }
}

interface UpdateRepositoryInterface {
    suspend fun downloadApk(): ResponseBody
}
