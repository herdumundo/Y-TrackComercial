package com.portalgm.y_trackcomercial.data.model.models.ventas

import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
data class DetalleCompleto(
    val docEntry: Long,
    val docEntryPedido: String?,
    val timbrado: String?,
    val qr: String?,
    val canceled: String?,
    val totalFacturaIvaIncluido: Double,
    val slpCode: Int?,
    val series: String?,
    val taxCode: String?,
    val cardCode: String?,
    val lineNumbCardCode: Int?,
    val idVisita: Long?,
    val numAtCard: String?,
    val cdc: String?,
    val xml: String?,
    val estado: String?,
    val contado: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val condicion: String?,
    val whsCode: String?,
    val totalLineaIvaIncluido: Int?,
    val lineNum: Int,
    val itemCode: String,
    val itemName: String?,
    val quantity: String,
    val precioUnitIvaIncluido: String?
)
data class DetalleLote(
    val docEntry: Long,
    val itemCode: String,
    val lote: String?,
    val quantity: String?,
    val estado: String?
)

fun transformarDatosConDetalle(datos: List<OinvPosWithDetails>): List<DetalleCompleto> {
    return datos.flatMap { oinv ->
        oinv.details.map { detail ->
            DetalleCompleto(
                docEntry = oinv.oinvPos.docEntry,
                docEntryPedido = oinv.oinvPos.docEntryPedido,
                timbrado = oinv.oinvPos.timb,
                qr = oinv.oinvPos.qr,
                canceled = oinv.oinvPos.anulado  ,
                totalFacturaIvaIncluido = oinv.oinvPos.totalIvaIncluido?.toDouble() ?: 0.0,
                slpCode = oinv.oinvPos.slpCode,
                series = oinv.oinvPos.series,
                taxCode = oinv.oinvPos.iva,
                cardCode = oinv.oinvPos.cardCode,
                lineNumbCardCode = oinv.oinvPos.lineNumbCardCode,
                idVisita = oinv.oinvPos.idVisita,
                numAtCard = oinv.oinvPos.numAtCard,
                cdc = oinv.oinvPos.cdc,
                xml = oinv.oinvPos.xmlFact,
                contado = oinv.oinvPos.contado,
                createdAt = oinv.oinvPos.docDate,
                updatedAt = oinv.oinvPos.docDate,
                condicion = oinv.oinvPos.condicion,
                lineNum = detail.lineNum,
                itemCode = detail.itemCode,
                itemName = detail.itemName,
                quantity = detail.quantity,
                precioUnitIvaIncluido = detail.precioUnitIvaInclu,
                whsCode = detail.whsCode,
                totalLineaIvaIncluido=detail.totalSinIva.toInt()+detail.totalIva.toInt(),
                estado = oinv.oinvPos.estado
                )
        }
    }
}
fun extraerLotes(datos: List<OinvPosWithDetails>): List<DetalleLote> {
    return datos.flatMap { oinv ->
        oinv.detailsLotes.map { lote ->
            DetalleLote(
                docEntry = lote.docEntry,
                itemCode = lote.itemCode,
                lote = lote.lote,
                quantity = lote.quantity ,
                estado = "P"
            )
        }
    }
}

data class DatosMovimientosOinv(
    val datosOinvInv1: List<DetalleCompleto>,
    val datosInv1Lotes: List<DetalleLote>
)
fun procesarDatos(datos: List<OinvPosWithDetails>): DatosMovimientosOinv {
    val detallesCompletos = transformarDatosConDetalle(datos)
    val lotesCompletos = extraerLotes(datos)
    return DatosMovimientosOinv(datosOinvInv1 = detallesCompletos, datosInv1Lotes = lotesCompletos)
}