package com.portalgm.y_trackcomercial.data.api.request

data class EnviarLotesDeUbicacionesNuevasRequest(
    val lotesDeUbicacionesNuevas: List<lotesDeUbicacionesNuevas>
)

data class lotesDeUbicacionesNuevas(
    val id: String?,
    val idUsuario: String?,
    val latitud: String?,
    val longitud: String?,
    val createdAt: String?,
    val idOcrd: String?
)
