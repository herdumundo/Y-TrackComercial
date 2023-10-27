package com.portalgm.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.OitmEntity
import com.portalgm.y_trackcomercial.data.model.models.OitmItem
@Dao
interface OitmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOitm(oitm: List<OitmEntity>)

    @Query("SELECT count(*) FROM oitm   ")
    fun getOitmCount(): Int


    @Query("delete FROM oitm   ")
    fun DeleteAllOitm()

    @Query("SELECT  *  FROM oitm  where CodeBars is not null  ")
    fun getOitm(): List<OitmItem>

    @Query("select  DISTINCT t1.ItemCode,t2.CodeBars,t2.ItemName   from ocrd_x_oitm t1 inner join oitm t2 on t1.ItemCode=t2.ItemCode inner join visitas t3 on t3.idOcrd=t1. CardCode where pendienteSincro='N' ")
    fun getOitmByPuntoVentaAbierto(): List<OitmItem>

}