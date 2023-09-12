package com.portalgm.y_trackcomercial.data.model.models

data class LotesItem(val ItemCode: String, val id: String?,val CodeBars: String)

data class Lotes(
    val ItemName: String,
    val itemCode: String,
    val Lote: String,
    val Cantidad: Int,
    val ubicacion: String,
    val idUsuario: Int,
    val userName: String,
    val tipoMovimiento: Int,
    val createdAt: String,
    val createdAtLong: Long,
    val codeBars: String,
    val idVisitas: Long,
    var loteLargo: String,
    val loteCorto: String,
    val obs: String,

    )
