package com.example.y_trackcomercial.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.registro_entities.MovimientosEntity

@Dao
interface MovimientosDao {


    @Transaction
    @Insert
    suspend fun insertAllMovimiento(Movimiento: List<MovimientosEntity>)
}