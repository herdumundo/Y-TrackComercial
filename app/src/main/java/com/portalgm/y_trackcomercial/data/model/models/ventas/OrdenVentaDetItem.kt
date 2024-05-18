package com.portalgm.y_trackcomercial.data.model.models.ventas

data class OrdenVentaDetItem(
    val id: String,
    val lineNumDet: Int,
    val licTradNum: String,
    val itemCode: String ,
    val itemName: String ,
    val quantity: String ,
    val quantityUnidad: Int,
    val priceAfVAT: String,
    val unitMsr: String,


    )



