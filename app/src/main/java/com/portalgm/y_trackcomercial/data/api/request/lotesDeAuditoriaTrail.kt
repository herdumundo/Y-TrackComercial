package com.portalgm.y_trackcomercial.data.api.request
data class EnviarAuditoriaTrailRequest(
    val lotesDeAuditoriaTrail: List<lotesDeAuditoriaTrail>
)

data class lotesDeAuditoriaTrail(
    val id: String?,
    val Fecha: String?,
    val fechaLong: String?,
    val idUsuario: String?,
    val latitud: String?,
    val longitud: String?,
    val velocidad: String?,
    val nombreUsuario: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val bateria: String?,
    val idVisita: String?,
    val distanciaPV: String?,
    val tiempo: String?,
    val tipoRegistro: String?
)
