package com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionAuditTrailApiClient
import com.portalgm.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgm.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class AuditTrailRepository @Inject constructor
    (private val auditTrailDao:  AuditTrailDao,
     private val exportacionAuditTrailApiClient: ExportacionAuditTrailApiClient, // Paso 1: Agregar el ApiClient al constructor
     private val sharedPreferences: SharedPreferences,
            ) {
    suspend fun insertAuditTrailDao(auditTrailEntity: com.portalgm.y_trackcomercial.data.model.entities.logs.AuditTrailEntity): Long {
        return auditTrailDao.insertAuditTrailDao(auditTrailEntity)
    }

   suspend fun getAuditTrailCount():  Int  {
        return auditTrailDao.getCountAuditTrail()
    }
    suspend fun getUltimaHoraRegistro():  Long  {
        return auditTrailDao.getUltimoRegistroHoraLong()
    }

    suspend fun getAllLotesAuditTrail(): List<lotesDeAuditoriaTrail> {
        val lotesDeAuditoriaEntity = auditTrailDao.getAllTrailExportar()

        return lotesDeAuditoriaEntity.map { entity ->
            lotesDeAuditoriaTrail(
                id = entity.id,
                Fecha = entity.Fecha,
                fechaLong =  entity.fechaLong,
                idUsuario = entity.idUsuario,
                latitud = entity.latitud,
                longitud = entity.longitud,
                velocidad = entity.velocidad,
                nombreUsuario = entity.nombreUsuario,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                bateria = entity.bateria,
               idVisita = entity.idVisita,
                distanciaPV = entity.distanciaPV,
                tiempo = entity.tiempo,
                tipoRegistro = entity.tipoRegistro
            )
        }
    }

    suspend fun exportarAuditTrail(lotesAuditTrail: EnviarAuditoriaTrailRequest) {
        try {
            val apiResponse = exportacionAuditTrailApiClient.uploadAuditoriaTrailData(lotesAuditTrail,sharedPreferences.getToken().toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
               auditTrailDao.updateExportadoCerrado()
            }
           // Log.i("MensajeTest",apiResponse.msg)
        } catch (e: Exception) {
            Log.i("Mensaje",e.toString())
        }
    }
}
