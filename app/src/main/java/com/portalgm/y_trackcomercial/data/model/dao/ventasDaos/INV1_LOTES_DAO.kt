package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.api.request.LoteDetalle
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
@Dao
interface INV1_LOTES_DAO {
    @Transaction
    @Insert
    suspend fun insertAll(Movimiento: List<INV1_LOTES_POS>)
/*
    @Query("    select  * from inv1_lotes_pos where docEntry=:docEntry")
    suspend fun getLotesPorDocEntry(docEntry: Long): List<LoteDetalle>
*/
}