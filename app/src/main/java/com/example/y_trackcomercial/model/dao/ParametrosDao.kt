package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.ParametrosEntity

@Dao
interface ParametrosDao {
    @Query("SELECT * FROM PARAMETROS   ")
    fun getParametros(): LiveData<List<ParametrosEntity>>


    @Query("SELECT count(*) FROM PARAMETROS   ")
    fun getParametrosCount():  Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllParametros(turnos: List<ParametrosEntity>)
}