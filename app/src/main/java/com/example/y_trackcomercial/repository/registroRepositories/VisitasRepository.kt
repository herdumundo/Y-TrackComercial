package com.example.y_trackcomercial.repository.registroRepositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.y_trackcomercial.data.network.exportaciones.ExportacionVisitasApiService
import com.example.y_trackcomercial.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity
import com.example.y_trackcomercial.model.models.EnviarVisitasRequest
import com.example.y_trackcomercial.model.models.lotesDeVisitas
import javax.inject.Inject

class VisitasRepository @Inject constructor
    (
    private val visitasDao: VisitasDao,
    private val exportacionVisitasApiService: ExportacionVisitasApiService // Paso 1: Agregar el ApiClient al constructor

) {


    suspend fun insertVisita(visita: VisitasEntity): Long {
        return visitasDao.insertVisitas(visita)
    }


    suspend fun getVisitaByEstado(estado: String): VisitasEntity? {
        return visitasDao.getVisitaByEstado(estado)
    }

    suspend fun getVisitaActiva(estado: String): VisitasEntity? {
        return visitasDao.getVisitaActiva(estado)
    }


    suspend fun getIdVisitaActiva(): Int {
        return visitasDao.getIdVisitaActiva()
    }

    suspend fun getSecuenciaVisita(): Int {
        return visitasDao.getSecuenciaVisita()
    }
    suspend fun updateVisita(visita: VisitasEntity) {
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

    fun getCantidadPendientesExportar(): Int {
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
                secuencia = entity.secuencia
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