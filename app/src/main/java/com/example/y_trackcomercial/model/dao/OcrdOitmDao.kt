package com.example.y_trackcomercial.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.OcrdOitmEntity

@Dao
interface OcrdOitmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllOcrdOitm(OcrdOitm: List<OcrdOitmEntity>)


    @Query("SELECT count(*) FROM OCRD_X_OITM   ")
    fun getOcrdOitmCount(): Int

    @Query("DELETE FROM OCRD_X_OITM")
    suspend fun deleteAllOcrdOitm()

}