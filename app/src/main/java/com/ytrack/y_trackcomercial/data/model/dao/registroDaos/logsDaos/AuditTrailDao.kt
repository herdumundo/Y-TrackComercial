package com.ytrack.y_trackcomercial.data.model.dao.registroDaos.logsDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ytrack.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail

@Dao
interface AuditTrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditTrailDao(auditTrailEntity: com.ytrack.y_trackcomercial.data.model.entities.logs.AuditTrailEntity): Long


    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    fun getAuditTrailCount(): Int

    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    suspend fun getCountAuditTrail(): Int



    @Query("select fechaLong as id,strftime('%Y-%m-%d %H:%M:%S', fecha)  as Fecha,FechaLong,idUsuario,latitud,longitud,velocidad,nombreUsuario,strftime('%Y-%m-%d %H:%M:%S', fecha)  as createdAt,  strftime('%Y-%m-%d %H:%M:%S', fecha)  as updatedAt,bateria  from audittrail where estado='P'")
    suspend fun  getAllTrailExportar():  List<lotesDeAuditoriaTrail>

    @Query("UPDATE audittrail SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )

}