package com.example.y_trackcomercial.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.UbicacionesPvEntity
import com.example.y_trackcomercial.model.models.OitmItem
import com.example.y_trackcomercial.model.models.UbicacionPv

@Dao
interface UbicacionesPvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllUbicacionesPv(ubicacionesPv: List<UbicacionesPvEntity>)

    @Query("SELECT count(*) FROM ubicaciones_pv   ")
    fun getUbicacionesPvCount(): Int


    @Query("select  * from UBICACIONES_PV  ")
    fun getUbicaciones(): List<UbicacionPv>



}