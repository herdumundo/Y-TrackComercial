package com.example.y_trackcomercial.data.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.data.model.entities.OcrdOitmEntity

@Dao
interface OcrdOitmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOcrdOitm(OcrdOitm: List<com.example.y_trackcomercial.data.model.entities.OcrdOitmEntity>)


    @Query("SELECT count(*) FROM OCRD_X_OITM   ")
    fun getOcrdOitmCount(): Int

    @Query("DELETE FROM OCRD_X_OITM")
    suspend fun deleteAllOcrdOitm()

}