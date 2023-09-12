package com.portalgm.y_trackcomercial.repository.registroRepositories

import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.NewPassDao
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class NewPassRepository @Inject constructor
    (
        private val newPassDao: NewPassDao,
        private val sharedPreferences: SharedPreferences,
       // private val exportacionNuevasUbicacionesApiClient: ExportacionNuevasUbicacionesApiClient // Paso 1: Agregar el ApiClient al constructor
    ) {
    suspend fun insertNewPass(newPassEntity: NewPassEntity): Long {
        return newPassDao.insertNewPass(newPassEntity)
    }

    suspend fun getCount(): Int {
        return newPassDao.getCountPendientes()
    }
/*
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
    */
}