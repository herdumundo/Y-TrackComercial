package com.example.y_trackcomercial.data.model.models

data class InformeInventario(
    val ocrdName: String,
    val id: String,
    val createdAt: String,
    val itemCode: String,
    val itemName: String,
    val codebar: String,
    val ubicacion: String,
    val cantidad: String,
    val estado: String,
    val lote: String
)
