package com.example.y_trackcomercial.repository.registroRepositories

import androidx.lifecycle.LiveData
import com.example.y_trackcomercial.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity
import javax.inject.Inject

class VisitasRepository @Inject constructor
    (private val visitasDao: VisitasDao) {


    suspend fun insertVisita(visita: VisitasEntity): Long {
        return visitasDao.insertVisitas(visita)
    }


    suspend fun getVisitaByEstado(estado: String): VisitasEntity? {
        return visitasDao.getVisitaByEstado(estado)
    }

    suspend fun getVisitaActiva(estado: String): VisitasEntity? {
        return visitasDao.getVisitaActiva(estado)
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
            visita.tipoRegistro
        )
    }


    fun getCantidadRegistrosPendientes(): LiveData<Int> {
        return visitasDao.getCantidadRegistrosPendientes()
    }
    fun getOcrdNameRepository(): LiveData<String> {
        return visitasDao.getOcrdPendiente()
    }
}
