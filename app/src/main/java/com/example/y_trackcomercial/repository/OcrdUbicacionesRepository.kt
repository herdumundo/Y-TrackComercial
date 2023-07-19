package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.OcrdUbicacionesApiClient
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdUbicacionesRepository @Inject constructor(
    private val OcrdUbicacionesService: OcrdUbicacionesApiClient,
    private val ocrdUbicacionesDao: OcrdUbicacionesDao
    ) {



    suspend fun fetchOcrdUbicaciones(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  OcrdUbicacionesService.getOcrdUbicaciones()
            ocrdUbicacionesDao.insertAllOcrdUbicaciones(ocrdUbicaciones)
            return@withContext getOcrdUbicacionesCount()
        }
    }



    fun getOcrdUbicacionesCount():  Int  {
        return ocrdUbicacionesDao.getOcrdUbicacionesCount()
    }
}