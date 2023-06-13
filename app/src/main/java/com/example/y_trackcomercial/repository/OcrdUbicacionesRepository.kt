package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.OcrdUbicacionesApiClient
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import javax.inject.Inject

class OcrdUbicacionesRepository @Inject constructor(
    private val OcrdUbicacionesService: OcrdUbicacionesApiClient,
    private val ocrdUbicacionesDao: OcrdUbicacionesDao
    ) {
     suspend fun fetchOcrdUbicaciones(progressCallback: (Float) -> Unit) {
        try {
            val ocrdUbicaciones = OcrdUbicacionesService.getOcrdUbicaciones()

            ocrdUbicacionesDao.insertAllOcrdUbicaciones(ocrdUbicaciones)
            progressCallback(100f)

        } catch (e: Exception) {
            // Manejar la excepción
        }
    }

    fun getOcrdUbicacionesCount():  Int  {
        return ocrdUbicacionesDao.getOcrdUbicacionesCount()
    }
}