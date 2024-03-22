package com.portalgm.y_trackcomercial.data.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails

@Dao
interface OinvPosDao {
    @Transaction
    @Query("SELECT * FROM OINV_POS")
    suspend fun getOinvPosWithDetails(): List<OinvPosWithDetails>

    // Si necesitas obtener los detalles para un solo registro de OINV_POS:
    @Transaction
    @Query("SELECT * FROM OINV_POS WHERE docEntry = :docEntry")
    fun getOinvPosWithDetailsByDocEntry(docEntry: Long): OinvPosWithDetails?
}
