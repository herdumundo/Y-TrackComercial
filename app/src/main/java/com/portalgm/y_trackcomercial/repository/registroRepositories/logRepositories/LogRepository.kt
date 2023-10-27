package com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionAuditLogApiClient
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgm.y_trackcomercial.data.api.request.lotesDeActividades
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao
import com.portalgm.y_trackcomercial.data.model.entities.logs.LogEntity
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class LogRepository @Inject constructor
    (private val logDao: LogDao,
     private val exportacionAuditLogApiClient: ExportacionAuditLogApiClient,
     private val sharedPreferences: SharedPreferences,

     ) {
    suspend fun insertLog(log:LogEntity): Long {
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
            val apiResponse = exportacionAuditLogApiClient.uploadAuditLoglData(lotesLog,sharedPreferences.getToken().toString())
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
