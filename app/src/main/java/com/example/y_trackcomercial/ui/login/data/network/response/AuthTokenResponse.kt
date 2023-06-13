package com.example.y_trackcomercial.ui.login2.data.network.response

import com.google.gson.annotations.SerializedName


data class AuthTokenResponse(
    @SerializedName("token") val token: String
)

