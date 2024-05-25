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

    @Query("SELECT docEntry FROM OINV_POS where estado='E'  ")
    suspend fun getOinvCancelacion():List<Long>

    @Query("SELECT docEntry FROM OINV_POS where estado='C' and anulado='N' and docEntrySap='-'  ")
    suspend fun getDocentriesNoProcesadosSap():List<Long>

    @Query("SELECT count(*) FROM OINV_POS where estado='E'  ")
    suspend fun getCountOinvCancelacion(): Int

    @Query("SELECT count(*) FROM OINV_POS where estado='C' and anulado='N' and docEntrySap='-'")
    suspend fun getCountDocentriesNoProcesadosSap(): Int


    // Si necesitas obtener los detalles para un solo registro de OINV_POS:
    @Transaction
    @Query("SELECT * FROM OINV_POS WHERE docEntry = :docEntry")
    fun getOinvPosWithDetailsByDocEntry(docEntry: Long): OinvPosWithDetails?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOinv(visita: OINV_POS): Long
    @Transaction
    @Query("UPDATE OINV_POS SET qr=:qr, xmlFact=:xml,cdc=:cdc,txtSifen=:txtSiedi,estado='P' WHERE docEntry = :docEntry")
    suspend fun updateFirmaFactura(
        qr:  String,
        xml:  String,
        cdc:  String,
        docEntry: Long,
        txtSiedi: String
    )

    @Query("SELECT * FROM OINV_POS where strftime('%Y-%m-%d', docDate)=:fecha")
    suspend fun getOinvPosByDate(fecha:String): List<OinvPosWithDetails>

    @Transaction
    @Query("UPDATE OINV_POS SET estado='E',anulado='Y' WHERE docEntry = :docEntry")
    suspend fun updateAnularFactura(docEntry: Long)

     @Query(" select estado from OINV_POS where docEntry=:docEntry")
     suspend fun getEstadoFacturaById(docEntry: Long): String

    @Transaction
    @Query("UPDATE OINV_POS SET estado='C',anulado='Y' WHERE docEntry = :docEntry")
    suspend fun updateAnularFacturaNoEnviadaAzure(docEntry: Long)

    @Query("SELECT count(*) FROM OINV_POS where estado IN ('P')")
    suspend fun getOinvCountPendientes(): Int

    @Query("SELECT count(*) FROM OINV_POS where estado IN ('E')")
    suspend fun getOinvCountPendientesCancelaciones(): Int

    @Query("UPDATE OINV_POS SET estado='C' WHERE  docEntry IN (:ids)")
    suspend fun updateExportadoCerradoPorLotes(ids: List<Long>)

    @Query("UPDATE OINV_POS SET docEntrySap=:docEntrySap WHERE  docEntry=:docEntry")
    suspend fun updateExportadoCerradoPorLotes(docEntrySap: Long,docEntry: Long)


}
