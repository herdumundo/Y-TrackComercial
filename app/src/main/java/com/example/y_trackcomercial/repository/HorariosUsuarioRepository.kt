package com.example.y_trackcomercial.repository

import javax.inject.Inject
import com.example.y_trackcomercial.data.network.HorariosUsuarioApiClient
import com.example.y_trackcomercial.model.dao.HorariosUsuarioDao
import com.example.y_trackcomercial.model.entities.HorariosUsuarioEntity
import com.example.y_trackcomercial.model.models.HorariosUsuarioResponse

class HorariosUsuarioRepository @Inject constructor(
    private val horariosUsuarioApiClient: HorariosUsuarioApiClient,
    private val horariosUsuarioDao: HorariosUsuarioDao
) {
    suspend fun fetchHorariosUsuario(idUsuario: Int) {
        try {
            val horariosResponse = horariosUsuarioApiClient.getHorarioUsuario(idUsuario)
            val horariosEntityList = convertToEntityList(horariosResponse)
            horariosUsuarioDao.insertAllTurnosHorarios(horariosEntityList)
        } catch (e: Exception) {
            val dasd = e.toString()
        }
    }
    fun getTurnosHorariosCountRepository():  Int  {
        return horariosUsuarioDao.getTurnosHorariosCount()
    }
    private fun convertToEntityList(horariosResponseList: List<HorariosUsuarioResponse>): List<HorariosUsuarioEntity> {
        val entityList = mutableListOf<HorariosUsuarioEntity>()
        for (horarioResponse in horariosResponseList) {
            for (yemsysHorario in horarioResponse.yemsys_horarios) {
                for (yemsysTurno in yemsysHorario.yemsys_turnos) {
                    val entity = HorariosUsuarioEntity(
                        id = yemsysTurno.id,
                        idHorario = yemsysHorario.id,
                        descripcion = yemsysHorario.descripcion,
                        horaEntrada = yemsysTurno.horaEnt,
                        horaSalida = yemsysTurno.horaSal,
                        toleranciaEntrada = yemsysTurno.tolEnt.toString(),
                        inicioEntrada = yemsysTurno.inicioEnt,
                        finEntrada = yemsysTurno.finEnt,
                        inicioSalida = yemsysTurno.inicioSal,
                        finSalida = yemsysTurno.finSal
                    )
                    entityList.add(entity)
                }
            }
        }
        return entityList
    }
}


