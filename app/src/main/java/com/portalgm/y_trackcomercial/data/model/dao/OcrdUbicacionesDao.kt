package com.portalgm.y_trackcomercial.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface OcrdUbicacionesDao {
    @Query("SELECT * FROM ocrd_ubicacion   ")
    fun getOcrdUbicaciones(): LiveData<List<com.portalgm.y_trackcomercial.data.model.entities.OcrdUbicacionEntity>>

    @Query("SELECT count(*) FROM ocrd_ubicacion   ")
    fun getOcrdUbicacionesCount():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertOcrdUbicaciones(ubicaciones: List<com.portalgm.y_trackcomercial.data.model.entities.OcrdUbicacionEntity>)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOcrdUbicaciones(ubicaciones: List<com.portalgm.y_trackcomercial.data.model.entities.OcrdUbicacionEntity>)

    @Query("DELETE FROM OCRD_UBICACION")
    suspend fun deleteAllOCRD_UBICACION()

}