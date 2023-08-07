package com.example.y_trackcomercial.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.data.model.entities.ParametrosEntity

@Dao
interface ParametrosDao {
    @Query("SELECT * FROM PARAMETROS   ")
    fun getParametros(): LiveData<List<com.example.y_trackcomercial.data.model.entities.ParametrosEntity>>


    @Query("SELECT count(*) FROM PARAMETROS   ")
    fun getParametrosCount():  Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllParametros(turnos: List<com.example.y_trackcomercial.data.model.entities.ParametrosEntity>)
}