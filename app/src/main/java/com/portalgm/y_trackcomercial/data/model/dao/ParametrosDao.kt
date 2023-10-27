package com.portalgm.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ParametrosEntity

@Dao
interface ParametrosDao {
    @Query("SELECT * FROM PARAMETROS   ")
    fun getParametros():  List<ParametrosEntity>

    @Query("SELECT count(*) FROM PARAMETROS   ")
    fun getParametrosCount():  Int

    @Query("SELECT  case when count(*)=0 then 60000 else valor end as valor FROM PARAMETROS where codigo='ANDROID_GPS_1'   ")
    suspend fun getParametroHiloGps1():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllParametros(turnos: List<ParametrosEntity>)
}