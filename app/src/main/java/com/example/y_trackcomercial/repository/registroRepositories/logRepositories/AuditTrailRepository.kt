package com.example.y_trackcomercial.repository.registroRepositories.logRepositories

import android.util.Log
import com.example.y_trackcomercial.data.api.exportaciones.ExportacionAuditTrailApiClient
import com.example.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.example.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import javax.inject.Inject

class AuditTrailRepository @Inject constructor
    (private val auditTrailDao: com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao,
     private val exportacionAuditTrailApiClient: ExportacionAuditTrailApiClient // Paso 1: Agregar el ApiClient al constructor
) {
    suspend fun insertAuditTrailDao(auditTrailEntity: com.example.y_trackcomercial.data.model.entities.logs.AuditTrailEntity): Long {
        return auditTrailDao.insertAuditTrailDao(auditTrailEntity)
    }

   suspend fun getAuditTrailCount():  Int  {
        return auditTrailDao.getCountAuditTrail()
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
                bateria = entity.bateria
            )
        }
    }

    suspend fun exportarAuditTrail(lotesAuditTrail: EnviarAuditoriaTrailRequest) {
        try {
            val apiResponse = exportacionAuditTrailApiClient.uploadAuditoriaTrailData(lotesAuditTrail)
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
               auditTrailDao.updateExportadoCerrado()
            } else {
                // Manejar otros casos según el valor de "tipo"
            }
            Log.i("MensajeTest",apiResponse.msg)
        } catch (e: Exception) {
            Log.i("Mensaje",e.toString())
        }
    }
}
