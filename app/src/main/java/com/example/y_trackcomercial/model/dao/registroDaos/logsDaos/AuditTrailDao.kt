package com.example.y_trackcomercial.model.dao.registroDaos.logsDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.y_trackcomercial.model.entities.logs.AuditTrailEntity

@Dao
interface AuditTrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditTrailDao(auditTrailEntity: AuditTrailEntity): Long


    @Query("SELECT COUNT(*) FROM AuditTrail")
    fun getAuditTrailCount(): Int

}