package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.UbicacionesPVClient
import com.example.y_trackcomercial.model.dao.UbicacionesPvDao
import com.example.y_trackcomercial.model.models.OcrdItem
import com.example.y_trackcomercial.model.models.UbicacionPv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UbicacionesPvRepository @Inject constructor(
    private val ubicacionesPVClient: UbicacionesPVClient,
    private val ubicacionesPvDao:  UbicacionesPvDao
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

    fun getUbicaciones(): List<UbicacionPv> {
        return ubicacionesPvDao.getUbicaciones()
    }
}