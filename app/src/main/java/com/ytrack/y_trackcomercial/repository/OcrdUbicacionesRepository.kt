package com.ytrack.y_trackcomercial.repository

import com.ytrack.y_trackcomercial.data.api.OcrdUbicacionesApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdUbicacionesRepository @Inject constructor(
    private val OcrdUbicacionesService: OcrdUbicacionesApiClient,
    private val ocrdUbicacionesDao: com.ytrack.y_trackcomercial.data.model.dao.OcrdUbicacionesDao
    ) {



    suspend fun fetchOcrdUbicaciones(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  OcrdUbicacionesService.getOcrdUbicaciones()
            ocrdUbicacionesDao.deleteAllOCRD_UBICACION()
            ocrdUbicacionesDao.insertAllOcrdUbicaciones(ocrdUbicaciones)
            return@withContext getOcrdUbicacionesCount()
        }
    }



    fun getOcrdUbicacionesCount():  Int  {
        return ocrdUbicacionesDao.getOcrdUbicacionesCount()
    }
}