package com.portalgm.y_trackcomercial.data.model.models.ventas

 data class Inv1Detalle(
    val docEntry: Long,
    val LineNum: Int,
    val ItemCode: String,
    val ItemName: String,
    val CodeBars: String,
    val WhsCode: String,
    val Quantity: Int,
    val PriceAfterVat: String,
    val PrecioUnitSinIva: Int,
    val PrecioUnitIvaInclu: String,
    val TotalSinIva: String,
    val TotalIva: String,
    val UoMEntry: Int,
    val uMedida: Int,
    var TaxCode: String
    )
