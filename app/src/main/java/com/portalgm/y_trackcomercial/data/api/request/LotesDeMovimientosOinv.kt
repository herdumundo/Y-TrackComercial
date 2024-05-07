package com.portalgm.y_trackcomercial.data.api.request


data class EnvioCompletoApi(
    val datosFacturas: List<DatosFacturaConLotes>
)
data class DatosFacturaConLotes(
    val datos_oinv_inv1: datos_oinv_inv1,
    val lotes: List<LoteDetalle>   // Detalles de los lotes asociados

)

data class datos_oinv_inv1(

    val docEntryPedido: String?,//OINV
    val docEntry: Long,//OINV
    val timbrado: String?,//OINV
    val whsCode: String?,//OINV
    val slpCode: String?,//OINV
    val series: String?,//OINV
    val taxCode: String?,//OINV
    val cardCode: String?,//OINV
    val lineNumbCardCode: Int,//OINV
    val idVisita: String?,//OINV
    val numAtCard: String?,//OINV
    val cdc: String?,//OINV
    val xml: String?,//OINV
    val qr: String?,//OINV
    val anulado: String?,//OINV
    val contado: String?,//OINV
    val estado: String?,//OINV
    val createdAt: String?,//OINV
    val updatedAt: String?,//OINV
    val totalFacturaIvaIncluido: String?,//OINV
    val lineNum: String?,//INV1
    val itemCode: String?,//INV1
    val itemName: String?,//INV1
    val quantity: String?,//INV1
    val totalLineaIvaIncluido: String?,//INV1
    val precioUnitIvaIncluido: String?,//INV1
)
data class LoteDetalle(
    val docEntry: Long,
    val itemCode: String,
    val lote: String?,
    val quantity: String
)