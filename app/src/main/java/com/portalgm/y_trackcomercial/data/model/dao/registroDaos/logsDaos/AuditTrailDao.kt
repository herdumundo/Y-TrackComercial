package com.portalgm.y_trackcomercial.data.model.dao.registroDaos.logsDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgm.y_trackcomercial.data.model.entities.logs.AuditTrailEntity

@Dao
interface AuditTrailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditTrailDao(auditTrailEntity: AuditTrailEntity): Long


    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    fun getAuditTrailCount(): Int

    @Query("SELECT COUNT(*) FROM AuditTrail where estado='P'")
    suspend fun getCountAuditTrail(): Int

    @Query("SELECT  fechaLong FROM audittrail ORDER BY id DESC LIMIT 1")
    suspend fun getUltimoRegistroHoraLong(): Long


    @Query("select fechaLong as id,strftime('%Y-%m-%d %H:%M:%S', fecha)  as Fecha,FechaLong,idUsuario,latitud,longitud,velocidad,nombreUsuario,strftime('%Y-%m-%d %H:%M:%S', fecha)  as createdAt,  strftime('%Y-%m-%d %H:%M:%S', fecha)  as updatedAt,bateria,idVisita,distanciaPV,tiempo,tipoRegistro  from audittrail where estado='P'")
    suspend fun  getAllTrailExportar():  List<lotesDeAuditoriaTrail>

    @Query("UPDATE audittrail SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )

    @Query("UPDATE audittrail SET estado='C' WHERE estado='P' AND fechaLong IN (:ids)")
    suspend fun updateExportadoCerradoPorLotes(ids: List<String>)
}