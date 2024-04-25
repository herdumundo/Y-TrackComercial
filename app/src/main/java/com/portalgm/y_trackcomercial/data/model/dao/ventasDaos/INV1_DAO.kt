package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
@Dao
interface INV1_DAO {
    @Transaction
    @Insert
    suspend fun insertAllInv1(Movimiento: List<INV1_POS>)
}