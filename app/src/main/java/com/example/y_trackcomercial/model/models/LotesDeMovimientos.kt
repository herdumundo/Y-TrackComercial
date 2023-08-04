package com.example.y_trackcomercial.model.models

data class EnviarLotesDeMovimientosRequest(
    val lotesDemovimientos: List<lotesDemovimientos>
)

data class lotesDemovimientos(
    val id: String?,
    val idUsuario: String?,
    val userName: String?,
    val idTipoMov: String?,
    val itemCode: String?,
    val codebar: String?,
    val createdAt: String?,
    val createdLong: String?,
    val updatedAt: String?,
    val ubicacion: String?,
    val itemName: String?,
    val cantidad: String?,
    val lote: String?,
    val idVisitas: String?,
    val loteLargo: String?,
    val loteCorto: String?,
    val obs: String?
)
