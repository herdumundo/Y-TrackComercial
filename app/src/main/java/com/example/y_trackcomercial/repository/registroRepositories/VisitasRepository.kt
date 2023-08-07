package com.example.y_trackcomercial.repository.registroRepositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.y_trackcomercial.data.api.exportaciones.ExportacionVisitasApiService
import com.example.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.example.y_trackcomercial.data.api.request.lotesDeVisitas
import javax.inject.Inject

class VisitasRepository @Inject constructor
    (
    private val visitasDao: com.example.y_trackcomercial.data.model.dao.registroDaos.VisitasDao,
    private val exportacionVisitasApiService: ExportacionVisitasApiService // Paso 1: Agregar el ApiClient al constructor

) {


    suspend fun insertVisita(visita: com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity): Long {
        return visitasDao.insertVisitas(visita)
    }


    suspend fun getVisitaByEstado(estado: String): com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity? {
        return visitasDao.getVisitaByEstado(estado)
    }

    suspend fun getVisitaActiva(estado: String): com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity? {
        return visitasDao.getVisitaActiva(estado)
    }


    suspend fun getIdVisitaActiva(): Long {
        return visitasDao.getIdVisitaActiva()
    }

    suspend fun getSecuenciaVisita(): Int {
        return visitasDao.getSecuenciaVisita()
    }
    suspend fun updateVisita(visita: com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity) {
        visitasDao.updateVisita(
            visita.id,
            visita.createdAt,
            visita.createdAtLong,
            visita.latitudUsuario,
            visita.longitudUsuario,
            visita.porcentajeBateria,
            visita.distanciaMetros,
            visita.pendienteSincro,
            visita.exportado,
            visita.tipoRegistro,
            visita.tipoCierre
        )
    }


    fun getCantidadRegistrosPendientes(): LiveData<Int> {
        return visitasDao.getCantidadRegistrosPendientes()
    }

    suspend fun getCantidadPendientesExportar(): Int {
        return visitasDao.getCantidadPendientesExportar()
    }

    fun getOcrdNameRepository(): LiveData<String> {
        return visitasDao.getOcrdPendiente()
    }

    suspend fun getAllLotesVisitas(): List<lotesDeVisitas> {
        val visitasEntities = visitasDao.getVisitasExportaciones()

        return visitasEntities.map { entity ->
            lotesDeVisitas(
                idUsuario = entity.idUsuario,
                idOcrd = entity.idOcrd,
                createdAt = entity.createdAt,
                createdAtLong = entity.createdAtLong,
                latitudUsuario = entity.latitudUsuario.toString(),
                longitudUsuario = entity.longitudUsuario.toString(),
                latitudPV = entity.latitudPV.toString(),
                longitudPV = entity.longitudPV.toString(),
                porcentajeBateria = entity.porcentajeBateria,
                idA = entity.idA,
                idRol = entity.idRol,
                tipoRegistro = entity.tipoRegistro,
                distanciaMetros = entity.distanciaMetros,
                estadoVisita = entity.estadoVisita ?: "",
                llegadaTardia = entity.tarde,
                idTurno = entity.idTurno,
                ocrdName = entity.ocrdName ?: "",
                tipoCierre = entity.tipoCierre,
                rol = entity.rol,
                secuencia = entity.secuencia,
                id=entity.id.toString()
            )
        }
    }
    suspend fun exportarVisitas(lotesVisitas: EnviarVisitasRequest) {
        try {
            val apiResponse = exportacionVisitasApiService.uploadVisitasData(lotesVisitas)
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                visitasDao.updateExportadoCerrado()
            } else {
                // Manejar otros casos según el valor de "tipo"
            }
            Log.i("MensajeTest",apiResponse.msg)
        } catch (e: Exception) {
           Log.i("Mensaje",e.toString())
        }
    }
}