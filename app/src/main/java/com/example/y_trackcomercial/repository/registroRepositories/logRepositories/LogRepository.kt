package com.example.y_trackcomercial.repository.registroRepositories.logRepositories

import android.util.Log
import com.example.y_trackcomercial.data.api.exportaciones.ExportacionAuditLogApiClient
import com.example.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.example.y_trackcomercial.data.api.request.lotesDeActividades
import javax.inject.Inject

class LogRepository @Inject constructor
    (private val logDao: com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao,
     private val exportacionAuditLogApiClient: ExportacionAuditLogApiClient
    ) {
    suspend fun insertLog(log: com.example.y_trackcomercial.data.model.entities.logs.LogEntity): Long {
        return logDao.insertLog(log)
    }

    suspend fun getCountLog():  Int  {
        return logDao.getCountActivitylog()
    }


    suspend fun getAllLotesAuditLog(): List<lotesDeActividades> {
        val lotesDeLogEntity = logDao.getAllAuditLogExportar()
        return lotesDeLogEntity.map { entity ->
            lotesDeActividades(
                id = entity.id,
                Fecha = entity.Fecha,
                fechaLong =  entity.fechaLong,
                etiqueta = entity.etiqueta,
                mensaje = entity.mensaje,
                idUsuario = entity.idUsuario,
                nombreUsuario = entity.nombreUsuario,
                componente = entity.componente,
                bateria = entity.bateria,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
            )
        }
    }

    suspend fun exportarLog(lotesLog: EnviarLotesDeActividadesRequest) {
        try {
            val apiResponse = exportacionAuditLogApiClient.uploadAuditLoglData(lotesLog)
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                logDao.updateExportadoCerrado()
            }
            Log.i("MensajeTest",apiResponse.msg)
        } catch (e: Exception) {
            Log.i("Mensaje",e.toString())
        }
    }
}
