package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.OcrdUbicacionesApiClient
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdUbicacionesRepository @Inject constructor(
    private val OcrdUbicacionesService: OcrdUbicacionesApiClient,
    private val sharedPreferences: SharedPreferences,
    private val ocrdUbicacionesDao: com.portalgm.y_trackcomercial.data.model.dao.OcrdUbicacionesDao
    ) {



    suspend fun fetchOcrdUbicaciones(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  OcrdUbicacionesService.getOcrdUbicaciones(sharedPreferences.getToken().toString())
            ocrdUbicacionesDao.deleteAllOCRD_UBICACION()
            ocrdUbicacionesDao.insertAllOcrdUbicaciones(ocrdUbicaciones)
            return@withContext getOcrdUbicacionesCount()
        }
    }



    fun getOcrdUbicacionesCount():  Int  {
        return ocrdUbicacionesDao.getOcrdUbicacionesCount()
    }
}