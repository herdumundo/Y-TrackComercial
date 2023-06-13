package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity

@Dao
interface OcrdUbicacionesDao {
    @Query("SELECT * FROM ocrd_ubicacion   ")
    fun getOcrdUbicaciones(): LiveData<List<OcrdUbicacionEntity>>

    @Query("SELECT count(*) FROM ocrd_ubicacion   ")
    fun getOcrdUbicacionesCount():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertOcrdUbicaciones(ubicaciones: List<OcrdUbicacionEntity>)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOcrdUbicaciones(ubicaciones: List<OcrdUbicacionEntity>)
}