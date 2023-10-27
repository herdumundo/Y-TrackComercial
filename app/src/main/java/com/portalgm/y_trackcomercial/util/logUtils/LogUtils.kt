package com.portalgm.y_trackcomercial.util.logUtils


import com.portalgm.y_trackcomercial.data.model.entities.logs.AuditTrailEntity
import com.portalgm.y_trackcomercial.data.model.entities.logs.LogEntity
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.util.SharedData

object LogUtils {

     suspend fun insertLog(
         logRepository: LogRepository,
         fecha: String,
         etiqueta: String,
         mensaje: String,
         idUsuario: Int,
         nombreUsuario: String,
         componente: String,
         porceBateria: Int
     ): Long {
        val log =  LogEntity(
            id = null,
            fecha = fecha,
            fechaLong = System.currentTimeMillis(),
            etiqueta = etiqueta,
            mensaje = mensaje,
            idUsuario = idUsuario,
            nombreUsuario = nombreUsuario,
            componente = componente,
            bateria = porceBateria,
            "P"
        )
        return logRepository.insertLog(log)
    }
    suspend fun insertLogAuditTrailUtils(auditTrailRepository: AuditTrailRepository,
                                         fecha: String, longitud: Double, latitud: Double, idUsuario: Int, nombreUsuario: String, velocidad: Double,bateria:Int,tipoRegistro:String): Long {
        val sharedData = SharedData.getInstance()

        val log = AuditTrailEntity(
            id = null,
            fecha = fecha,
            fechaLong = System.currentTimeMillis(),
            longitud = longitud,
            latitud = latitud,
            usuarioId = idUsuario,
            velocidad = velocidad * 3.6,
            nombreUsuario = nombreUsuario,
            estado = "P",
            bateria = bateria,
             idVisita = sharedData.idVisitaGlobal.value ?:0,
            distanciaPV = sharedData.distanciaPV.value ?:1000000 ,
            tiempo = sharedData.tiempo.value ?:0,
            tipoRegistro = tipoRegistro
        )
        return auditTrailRepository.insertAuditTrailDao(log)
    }
}

