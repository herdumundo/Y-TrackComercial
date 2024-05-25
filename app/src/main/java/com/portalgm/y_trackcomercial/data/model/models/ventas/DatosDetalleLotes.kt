package com.portalgm.y_trackcomercial.data.model.models.ventas

data class DatosDetalleLotes(
    val itemName: String?,
    val itemCode: String?,
    val distNumber: String? ,
    val loteLargo: String? ,
    val whsCode: String? ,
    val quantity: String?
 )

data class DatosItemCodesStock(
    val itemName: String?,
    val itemCode: String?,
    val quantity: String?
)

data class DatosItemCodesStockPriceList(
    val itemName: String?,
    val itemCode: String?,
    val quantity: String?,
    val price: String?,
    val priceList: Int,
    val unitMsr: String?,
    val baseQty : Int,

)