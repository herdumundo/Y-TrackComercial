package com.example.y_trackcomercial.util.logUtils

import com.example.y_trackcomercial.model.entities.logs.AuditTrailEntity
import com.example.y_trackcomercial.model.entities.logs.LogEntity
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.util.SharedPreferences

object LogUtils {

     suspend fun insertLog(logRepository: LogRepository, fecha: String, etiqueta: String, mensaje: String, idUsuario: Int, nombreUsuario: String, componente: String): Long {
        val log = LogEntity(
            id = null,
            fecha = fecha,
            fechaLong = System.currentTimeMillis(),
            etiqueta = etiqueta,
            mensaje = mensaje,
            idUsuario = idUsuario,
            nombreUsuario = nombreUsuario,
            componente = componente
        )
        return logRepository.insertLog(log)
    }
    suspend fun insertLogAuditTrailUtils(auditTrailRepository: AuditTrailRepository,
                                         fecha: String, longitud: Double, latitud: Double, idUsuario: Int, nombreUsuario: String, velocidad: Double): Long {
        val log = AuditTrailEntity(
            id = null,
            fecha = fecha,
            fechaLong = System.currentTimeMillis(),
            longitud = longitud,
            latitud = latitud,
            usuarioId = idUsuario,
            velocidad = velocidad * 3.6  ,
            nombreUsuario = nombreUsuario,
            estado="P"
        )
        return auditTrailRepository.insertAuditTrailDao(log)
    }
}

