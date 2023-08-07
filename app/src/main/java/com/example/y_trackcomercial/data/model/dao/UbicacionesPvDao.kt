package com.example.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.data.model.entities.UbicacionesPvEntity
import com.example.y_trackcomercial.data.model.models.OitmItem
import com.example.y_trackcomercial.data.model.models.UbicacionPv

@Dao
interface UbicacionesPvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllUbicacionesPv(ubicacionesPv: List<com.example.y_trackcomercial.data.model.entities.UbicacionesPvEntity>)

    @Query("SELECT count(*) FROM ubicaciones_pv   ")
    fun getUbicacionesPvCount(): Int


    @Query("select  * from UBICACIONES_PV  ")
    fun getUbicaciones(): List<com.example.y_trackcomercial.data.model.models.UbicacionPv>



}