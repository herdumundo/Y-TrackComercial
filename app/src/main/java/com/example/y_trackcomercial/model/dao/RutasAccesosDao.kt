package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity

@Dao
interface RutasAccesosDao {
    @Query("SELECT * FROM rutasaccesos   ")
    fun getAllRutasaccesos(): List<RutasAccesosEntity>

    @Query("SELECT count(*) FROM rutasaccesos   ")
    fun getRutasAccesosCount():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllRutasAccesos(rutasAccesos: List<RutasAccesosEntity>)
}