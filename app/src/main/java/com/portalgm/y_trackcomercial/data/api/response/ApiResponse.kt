package com.portalgm.y_trackcomercial.data.api.response

data class ApiResponse(
    val msg: String,
    val tipo: Int
)

data class ApiResponseFacturacion(
    val msg: String,
    val tipo: Int,
    val docEntries: List<String> // Suponiendo que docEntries es una lista de enteros
)


data class ApiKeyGMSResponse(
    val apiKey: String,
)


data class ApiResponseLimiteCreditoNroFactura(
    val ult_nro_fact: Int,
    val cardCode: String,
    val creditLine: Long,
    val totalFactura: Long,
    val Balance: Long,
    val CreditDisp: Long,
 )