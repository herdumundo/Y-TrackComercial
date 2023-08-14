package com.ytrack.y_trackcomercial.repository

import com.ytrack.y_trackcomercial.data.api.UbicacionesPVClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UbicacionesPvRepository @Inject constructor(
    private val ubicacionesPVClient: UbicacionesPVClient,
    private val ubicacionesPvDao: com.ytrack.y_trackcomercial.data.model.dao.UbicacionesPvDao
    ) {
    suspend fun fetchUbicacionesPv(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  ubicacionesPVClient.getUbicacionesPv()
            ubicacionesPvDao.insertAllUbicacionesPv(ocrdUbicaciones)
            return@withContext getUbicacionesPvCount()
        }
    }
    fun getUbicacionesPvCount():  Int  {
        return ubicacionesPvDao.getUbicacionesPvCount()
    }

    fun getUbicaciones(): List<com.ytrack.y_trackcomercial.data.model.models.UbicacionPv> {
        return ubicacionesPvDao.getUbicaciones()
    }
}