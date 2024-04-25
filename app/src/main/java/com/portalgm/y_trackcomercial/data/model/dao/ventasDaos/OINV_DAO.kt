package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails

@Dao
interface OINV_DAO {
    @Transaction
    @Query("SELECT * FROM OINV_POS where docEntry=:docEntry")
    suspend fun getOinvPosWithDetails(docEntry:Long): List<OinvPosWithDetails>
    // Si necesitas obtener los detalles para un solo registro de OINV_POS:
    @Transaction
    @Query("SELECT * FROM OINV_POS WHERE docEntry = :docEntry")
    fun getOinvPosWithDetailsByDocEntry(docEntry: Long): OinvPosWithDetails?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOinv(visita: OINV_POS): Long
    @Transaction
    @Query("UPDATE OINV_POS SET qr=:qr, xmlFact=:xml,cdc=:cdc WHERE docEntry = :docEntry")
    suspend fun updateFirmaFactura(qr: String,xml:String,cdc:String,docEntry: Long)

    @Query("SELECT * FROM OINV_POS where strftime('%Y-%m-%d', docDate)=:fecha")
    suspend fun getOinvPosByDate(fecha:String): List<OinvPosWithDetails>
}
