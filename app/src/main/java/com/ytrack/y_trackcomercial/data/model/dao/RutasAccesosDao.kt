package com.ytrack.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RutasAccesosDao {
    @Query("SELECT * FROM rutasaccesos   ")
    fun getAllRutasaccesos(): List<com.ytrack.y_trackcomercial.data.model.entities.RutasAccesosEntity>

    @Query("SELECT count(*) FROM rutasaccesos   ")
    fun getRutasAccesosCount():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllRutasAccesos(rutasAccesos: List<com.ytrack.y_trackcomercial.data.model.entities.RutasAccesosEntity>)


    @Query("DELETE FROM rutasaccesos")
    suspend fun deleteAllRutasaccesos()

}