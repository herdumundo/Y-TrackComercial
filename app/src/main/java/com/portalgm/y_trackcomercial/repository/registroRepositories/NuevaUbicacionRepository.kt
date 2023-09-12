package com.portalgm.y_trackcomercial.repository.registroRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionNuevasUbicacionesApiClient
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgm.y_trackcomercial.data.api.request.lotesDeUbicacionesNuevas
import com.portalgm.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.UbicacionesNuevasDao
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class NuevaUbicacionRepository @Inject constructor
    (
        private val ubicacionesNuevasDao: UbicacionesNuevasDao,
        private val sharedPreferences: SharedPreferences,
        private val exportacionNuevasUbicacionesApiClient: ExportacionNuevasUbicacionesApiClient // Paso 1: Agregar el ApiClient al constructor
    ) {
    suspend fun insertNuevaUbicacion(nuevaUbicacion: UbicacionesNuevasEntity): Long {
        return ubicacionesNuevasDao.insertNuevaUbicacion(nuevaUbicacion)
    }

    suspend fun getCount(): Int {
        return ubicacionesNuevasDao.getCountPendientes()
    }

    suspend fun getAllLotesNuevasUbicaciones(): List<lotesDeUbicacionesNuevas> {
        val lotesDeUbicacionesNuevasEntity = ubicacionesNuevasDao.getAllNuevasUbicacionesExportar()
        return lotesDeUbicacionesNuevasEntity.map { entity ->
            lotesDeUbicacionesNuevas(
                id = entity.id,
                idUsuario = entity.idUsuario,
                createdAt = entity.createdAt,
                latitud = entity.latitud,
                longitud = entity.longitud,
                idOcrd = entity.idOcrd
            )
        }
    }



    suspend fun exportarMovimientos(lotes: EnviarLotesDeUbicacionesNuevasRequest) {
        try {
            val apiResponse = exportacionNuevasUbicacionesApiClient.uploadNuevasUbicaciones(lotes,sharedPreferences.getToken().toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                ubicacionesNuevasDao.updateExportadoCerrado()
            }
            Log.i("MensajeTest", apiResponse.msg)
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }
}