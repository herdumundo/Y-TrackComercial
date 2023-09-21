package com.portalgm.y_trackcomercial.data.api.request
data class EnviarVisitasRequest(
    val lotesDeVisitas: List<lotesDeVisitas>
)

data class lotesDeVisitas(
    val id:String,
    val idUsuario: Int,
    val idOcrd: String,
    val createdAt: String,
     val createdAtLong: Long,
    val latitudUsuario: String,
    val longitudUsuario: String,
    val latitudPV: String,
    val longitudPV: String,
    val porcentajeBateria: Int,
    val idA: Long,
    val idRol: Int,
    val tipoRegistro: String,
    val distanciaMetros: Int?,
    val estadoVisita: String,
    val llegadaTardia: String,
    val idTurno: Int,
    val ocrdName: String,
    val tipoCierre: String,
    val rol: String,
    val secuencia: Int,
    val versionApp: String,
    )
