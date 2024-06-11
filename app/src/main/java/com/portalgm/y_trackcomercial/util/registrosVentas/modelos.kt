package com.portalgm.y_trackcomercial.util.registrosVentas


data class ProductoItem(
    val lineNum: Int,
    val name: String,
    val itemCode: String,
    val price: Double,
    val initialQuantity: Number,
    val quantityUnidad: Int,
    val unitMsr: String,
    val CodeBars: String,
    val uMedida: Int,

    )