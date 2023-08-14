package com.ytrack.y_trackcomercial.repository

import android.util.Log
import javax.inject.Inject
import com.ytrack.y_trackcomercial.data.api.HorariosUsuarioApiClient
import com.ytrack.y_trackcomercial.util.HoraActualUtils
import com.ytrack.y_trackcomercial.util.HoraActualUtils.convertirStringToLocalTime
import com.ytrack.y_trackcomercial.util.ValidacionesVisitas.ValidacionInicioHoraResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class HorariosUsuarioRepository @Inject constructor(
    private val horariosUsuarioApiClient: HorariosUsuarioApiClient,
    private val visitasDao: com.ytrack.y_trackcomercial.data.model.dao.registroDaos.VisitasDao,
    private val horariosUsuarioDao: com.ytrack.y_trackcomercial.data.model.dao.HorariosUsuarioDao
) {

    suspend fun fetchHorariosUsuario(idUsuario: Int): Int {
        return withContext(Dispatchers.IO) {
            try {
                val horariosResponse = horariosUsuarioApiClient.getHorarioUsuario(idUsuario)
                val horariosEntityList = convertToEntityList(horariosResponse)
                horariosUsuarioDao.insertAllTurnosHorarios(horariosEntityList)
                return@withContext getTurnosHorariosCountRepository()
            } catch (e: Exception) {
                // Aquí puedes manejar la excepción de acuerdo a tus necesidades
                // Por ejemplo, imprimir un mensaje de error o lanzar una excepción personalizada
                var msg =e.toString()
                Log.i("msg",msg)
                // O retornar un valor específico en caso de error
                return@withContext -1
            }
        }
    }
    /*suspend fun fetchHorariosUsuario(idUsuario: Int): Int {
        return withContext(Dispatchers.IO) {

            val horariosResponse = horariosUsuarioApiClient.getHorarioUsuario(idUsuario)
            val horariosEntityList = convertToEntityList(horariosResponse)
            horariosUsuarioDao.insertAllTurnosHorarios(horariosEntityList)
            return@withContext getTurnosHorariosCountRepository()
        }
    }*/

    fun getTurnosHorariosCountRepository(): Int {
        return horariosUsuarioDao.getTurnosHorariosCount()
    }

    private fun convertToEntityList(horariosResponseList: List<com.ytrack.y_trackcomercial.data.model.models.HorariosUsuarioResponse>): List<com.ytrack.y_trackcomercial.data.model.entities.HorariosUsuarioEntity> {
        val entityList = mutableListOf<com.ytrack.y_trackcomercial.data.model.entities.HorariosUsuarioEntity>()
        for (horarioResponse in horariosResponseList) {
            for (yemsysHorario in horarioResponse.yemsys_horarios) {
                for (yemsysTurno in yemsysHorario.yemsys_turnos) {
                    val entity =
                        com.ytrack.y_trackcomercial.data.model.entities.HorariosUsuarioEntity(
                            id = yemsysTurno.id,
                            idHorario = yemsysHorario.id,
                            descripcion = yemsysHorario.descripcion,
                            horaEntrada = yemsysTurno.horaEnt,
                            horaSalida = yemsysTurno.horaSal,
                            toleranciaEntrada = yemsysTurno.tolEnt.toString(),
                            toleranciaSalida = yemsysTurno.tolSal.toString(),
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

    /** Estados de respuestas.
    *
     *  1 = Permite iniciar visita tras la validaciones, o permite porque no es la primera visita del turno.
    *
     *  2 = Llegada tarde.
    *
     *  3 = Muy temprano para iniciar visita.
    *
     *  4 = No existen turnos para el horario en que se intenta iniciar visita.
    * */
    suspend fun validacionInicioHora(esPrimeraVisita: Boolean): ValidacionInicioHoraResult {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        return withContext(Dispatchers.IO) {
            val horaActual = HoraActualUtils.getCurrentFormattedTime()
            val horarioActualList = horariosUsuarioDao.getHorarioActual(horaActual)

            var respuestaVisita = 0
            var mensaje = ""

            if (horarioActualList.isEmpty()&&esPrimeraVisita) {
                // La lista está vacía, no hay datos disponibles
                respuestaVisita = 4
                mensaje = "NO EXISTEN TURNOS PARA ESE HORARIO."
            }

            else if(horarioActualList.isEmpty())
            {
                respuestaVisita = 4
                mensaje = "NO EXISTEN TURNOS PARA ESE HORARIO."
            }
            else {
                val horaEntradaLocalTime = convertirStringToLocalTime(
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(dateFormat.parse(horarioActualList[0].horaEntrada.trim()))
                )

                val horaInicioLocalTime = convertirStringToLocalTime(
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(dateFormat.parse(horarioActualList[0].inicioEntrada.trim()))
                )
                val horaActualLocalTime = convertirStringToLocalTime(
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(dateFormat.parse(horaActual))
                )
                // SE AGREGAN A LA HORA DE ENTRADA LOS MINUTOS DE TOLERANCIA
                val horaEntradaMas5Minutos =
                    horaEntradaLocalTime.plusMinutes(horarioActualList[0].toleranciaEntrada.trim().toInt().toLong())

                if (esPrimeraVisita) {
                    // SI LA HORA DE ENTRADA + MINUTOS DE TOLERANCIA ES ANTES O MENOR QUE LA HORA ACTUAL
                    if (horaEntradaMas5Minutos.isBefore(horaActualLocalTime)) {
                        respuestaVisita = 2
                        mensaje = "LLEGASTE TARDE, LA TOLERANCIA ES HASTA LAS $horaEntradaMas5Minutos"
                    }
                    // SI LA HORA ACTUAL ES ANTES O MENOR A LA HORA MINIMA DE INICIO DE JORNADA
                    else if (horaActualLocalTime.isBefore(horaInicioLocalTime)) {
                        respuestaVisita = 3
                        mensaje = "NO PUEDES MARCAR ASISTENCIA AÚN, ES DEMASIADO TEMPRANO"
                    } else {
                        respuestaVisita = 1
                        mensaje = "PERMITIR VISITA."
                    }
                } else {
                    respuestaVisita = 1
                    mensaje = "PERMITIR VISITA. NO ES LA PRIMERA VISITA PARA ESE TURNO"
                }
            }
            return@withContext ValidacionInicioHoraResult(respuestaVisita, mensaje)
        }
    }

    suspend fun esPrimeraVisitaTurno(idTurno: Int): Boolean {
        val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaActual = LocalDateTime.now().format(dateFormat)

        return withContext(Dispatchers.IO) {
            visitasDao.getEsPrimeraVisita(fechaActual, idTurno) == 0
        }
    }

    suspend fun getIdTurno(): Int {
        val horaActual = HoraActualUtils.getCurrentFormattedTime()
        return withContext(Dispatchers.IO) {
            horariosUsuarioDao.getIdTurno(horaActual)
        }
    }

}


