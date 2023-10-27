package com.portalgm.y_trackcomercial.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.LotesListasEntity
import com.portalgm.y_trackcomercial.data.model.models.LotesItem

@Dao
interface LotesListasDao {
    @Query("SELECT * FROM LOTES_LISTA")
    fun getLotesListas(): LiveData<List<LotesListasEntity>>

    @Query("SELECT * FROM LOTES_LISTA WHERE ItemCode = :itemCode")
    fun getLotesListasByItemCode(itemCode: String): List<LotesItem>

    @Query("SELECT COUNT(*) FROM LOTES_LISTA")
    fun getLotesListasCount(): Int

    @Query("DELETE FROM  LOTES_LISTA")
    fun getDeleteLotesLista()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllLotesListas(customers: List<LotesListasEntity>)



}
