package com.example.y_trackcomercial.model.dao.registroDaos.logsDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.y_trackcomercial.model.entities.logs.AuditTrailEntity
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity
import com.example.y_trackcomercial.model.models.lotesDeAuditoriaTrail

@Dao
interface AuditTrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditTrailDao(auditTrailEntity: AuditTrailEntity): Long


    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    fun getAuditTrailCount(): Int

    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    suspend fun getCountAuditTrail(): Int



    @Query("select fechaLong as id,strftime('%Y-%m-%d %H:%M:%S', fecha)  as Fecha,FechaLong,idUsuario,latitud,longitud,velocidad,nombreUsuario,strftime('%Y-%m-%d %H:%M:%S', fecha)  as createdAt,  strftime('%Y-%m-%d %H:%M:%S', fecha)  as updatedAt  from audittrail where estado='P'")
    suspend fun  getAllTrailExportar():  List<lotesDeAuditoriaTrail>

    @Query("UPDATE audittrail SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )

}