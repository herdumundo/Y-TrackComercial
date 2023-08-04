package com.example.y_trackcomercial.model.models
data class EnviarLotesDeActividadesRequest(
    val lotesDeActividades: List<lotesDeActividades>
)

data class lotesDeActividades(
    val id: String?,
    val Fecha : String?,
    val fechaLong : String?,
    val etiqueta : String?,
    val mensaje : String?,
    val idUsuario : String?,
    val nombreUsuario : String?,
    val componente : String?,
    val bateria : String?,
    val createdAt : String?,
    val updatedAt : String?
)
