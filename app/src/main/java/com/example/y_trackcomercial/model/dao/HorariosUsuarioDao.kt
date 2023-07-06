package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.HorariosUsuarioEntity

@Dao
interface HorariosUsuarioDao {
    @Query("SELECT * FROM turnosHorarios   ")
    fun getHorarioUsuario(): LiveData<List<HorariosUsuarioEntity>>


    @Query("SELECT count(*) FROM turnosHorarios   ")
    fun getTurnosHorariosCount():  Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllTurnosHorarios(turnos: List<HorariosUsuarioEntity>)
}