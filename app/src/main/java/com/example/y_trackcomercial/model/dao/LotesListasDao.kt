package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.LotesListasEntity

@Dao
interface LotesListasDao {
    @Query("SELECT * FROM LOTES_LISTA   ")
    fun getLotesListas(): LiveData<List<LotesListasEntity>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllLotesListas(customers: List<LotesListasEntity>)
}