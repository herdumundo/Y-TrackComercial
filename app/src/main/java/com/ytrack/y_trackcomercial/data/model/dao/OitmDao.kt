package com.ytrack.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ytrack.y_trackcomercial.data.model.models.OitmItem

@Dao
interface OitmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOitm(oitm: List<com.ytrack.y_trackcomercial.data.model.entities.OitmEntity>)


    @Query("SELECT count(*) FROM oitm   ")
    fun getOitmCount(): Int


    @Query("SELECT  *  FROM oitm  where CodeBars is not null  ")
    fun getOitm(): List<com.ytrack.y_trackcomercial.data.model.models.OitmItem>

    @Query("select  DISTINCT t1.ItemCode,t2.CodeBars,t2.ItemName   from ocrd_x_oitm t1 inner join oitm t2 on t1.ItemCode=t2.ItemCode  where ShipToCode   in( select ocrdName from visitas where pendienteSincro='N') ")
    fun getOitmByPuntoVentaAbierto(): List<OitmItem>





}