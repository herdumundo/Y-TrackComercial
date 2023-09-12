package com.portalgm.y_trackcomercial.data.api.response

data class ApiResponse(
    val msg: String,
    val tipo: Int
)

data class ApiKeyGMSResponse(
    val apiKey: String,
)