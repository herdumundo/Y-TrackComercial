package com.portalgm.y_trackcomercial.data.api.response

data class ApiResponseOcrd(
    val id: String,
    val Address: String,
    val CardCode: String,
    val CardName: String?,
    val CreditDisp: String?,
    val CreditLine: String?,
    val Balance: String?,
    val U_SIFENCIUDAD: String?,
    val U_DEPTOCOD: String?,
    val U_SIFENNCASA: String?,
    val correo: String?,
    val ListNum: Int,
    val cardCode2: String?,
    val LineNum: Int?,
    val LicTradNum: String?,
    val GroupNum: Int?,
    val PymntGroup: String?,
    val STREET: String?,

    val OCRD_UBICACION: OcrdUbicacionResponse
)

data class OcrdUbicacionResponse(
    val id: Int,
    val idCab: String,
    val latitud: String,
    val longitud: String
)