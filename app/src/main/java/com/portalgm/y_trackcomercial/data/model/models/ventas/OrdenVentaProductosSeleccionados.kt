package com.portalgm.y_trackcomercial.data.model.models.ventas

data class OrdenVentaProductosSeleccionados(
    val lineNumDet: Int,
    val itemCode: String ,
    val itemName: String ,
    val quantity: Int ,
    val priceAfVAT: Int)



