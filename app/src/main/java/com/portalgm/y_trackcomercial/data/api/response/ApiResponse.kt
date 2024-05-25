package com.portalgm.y_trackcomercial.data.api.response

data class ApiResponse(
    val msg: String,
    val tipo: Int
)

data class ApiResponseFacturacion(
    val msg: String,
    val tipo: Int,
    val docEntries: List<Long> // Suponiendo que docEntries es una lista de enteros
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


data class DocEntry(
    val docEntry: Long,
    val docEntrySap: Long
)

data class ApiResponseFacturaProcesadaSap(
    val tipo: Int,
    val msg: String,
    val docEntries: List<DocEntry>
)