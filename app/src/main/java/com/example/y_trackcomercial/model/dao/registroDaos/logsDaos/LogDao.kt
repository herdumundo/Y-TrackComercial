package com.example.y_trackcomercial.model.dao.registroDaos.logsDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.y_trackcomercial.model.entities.logs.LogEntity
import com.example.y_trackcomercial.model.models.lotesDeActividades
import com.example.y_trackcomercial.model.models.lotesDeAuditoriaTrail

@Dao
interface LogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogEntity): Long

    @Query("SELECT COUNT(*) FROM activitylog where estado='P'")
    suspend fun getCountActivitylog(): Int

    @Query("select fechaLong as id,strftime('%Y-%m-%d %H:%M:%S', fecha)  as Fecha,fechaLong,etiqueta,mensaje, idUsuario,nombreUsuario,componente,bateria,strftime('%Y-%m-%d %H:%M:%S', fecha)  as createdAt,  strftime('%Y-%m-%d %H:%M:%S', fecha)  as updatedAt  from activitylog where estado='P'")
    suspend fun  getAllAuditLogExportar():  List<lotesDeActividades>

    @Query("UPDATE activitylog SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )
}