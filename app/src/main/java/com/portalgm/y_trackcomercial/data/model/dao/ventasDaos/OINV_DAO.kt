package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.api.request.datos_oinv_inv1
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails

@Dao
interface OINV_DAO {
    @Transaction
    @Query("SELECT * FROM OINV_POS where docEntry=:docEntry  ")
    suspend fun getOinvPosWithDetails(docEntry:Long): List<OinvPosWithDetails>


    @Query("SELECT * FROM OINV_POS where estado='P'  ")
    suspend fun getOinvInv1Lotes(): List<OinvPosWithDetails>


    // Si necesitas obtener los detalles para un solo registro de OINV_POS:
    @Transaction
    @Query("SELECT * FROM OINV_POS WHERE docEntry = :docEntry")
    fun getOinvPosWithDetailsByDocEntry(docEntry: Long): OinvPosWithDetails?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOinv(visita: OINV_POS): Long
    @Transaction
    @Query("UPDATE OINV_POS SET qr=:qr, xmlFact=:xml,cdc=:cdc,estado='P' WHERE docEntry = :docEntry")
    suspend fun updateFirmaFactura(qr: String,xml:String,cdc:String,docEntry: Long)

    @Query("SELECT * FROM OINV_POS where strftime('%Y-%m-%d', docDate)=:fecha")
    suspend fun getOinvPosByDate(fecha:String): List<OinvPosWithDetails>

    @Transaction
    @Query("UPDATE OINV_POS SET estado='E',anulado='Y' WHERE docEntry = :docEntry")
    suspend fun updateAnularFactura(docEntry: Long)
/*
    @Query("    select " +
            "       T0.idVisita,T0.docEntry,T0.docEntryPedido,T0.lineNumbCardCode,T0.timb as timbrado,T0.cardCode, T0.numAtCard,T0.totalIvaIncluido as totalFacturaIvaIncluido," +
            "       T0.cdc,T0.xmlFact as xml,T0.qr,T0.anulado,T0.contado,T0.docDate as createdAt,T0.docDate as updatedAt," +
            "       T2.lineNum as lineNum,T2.itemCode as itemCode,T2.itemName as itemName ,T2.quantity as quantity,(T2.totalSinIva+T2.totalIva) as totalLineaIvaIncluido, " +
            "       T2.PrecioUnitIvaInclu as precioUnitIvaIncluido,T2.totalSinIva, T2.totalIva,T0.estado,T2.whsCode as whsCode,T0.slpCode,T0.series,T0.iva as taxCode" +
            "   from oinv_pos T0 INNER JOIN INV1_POS T2 ON T0.docEntry=T2.docEntry")
    suspend fun getAllFacturasPendientesExportar(): List<datos_oinv_inv1>
*/
    @Query("SELECT count(*) FROM OINV_POS where estado='P'")
    suspend fun getOinvCountPendientes(): Int

    @Query("UPDATE OINV_POS SET estado='C' WHERE  docEntry IN (:ids)")
    suspend fun updateExportadoCerradoPorLotes(ids: List<String>)
}
