package com.portalgm.y_trackcomercial.data.model.models.ventas

data class OrdenVentaProductosSeleccionados(
    val lineNumDet: Int,
    val itemCode: String ,
    val CodeBars: String ,
    val itemName: String ,
    val quantity: Number ,
    val quantityUnidad: Int ,
    val priceAfVAT: Double,
    val unitMsr: String,
    val uMedida: Int

)



