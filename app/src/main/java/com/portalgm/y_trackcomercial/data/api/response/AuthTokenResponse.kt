package com.portalgm.y_trackcomercial.data.api.response

import com.google.gson.annotations.SerializedName


data class AuthTokenResponse(
    @SerializedName("token") val token: String
)

