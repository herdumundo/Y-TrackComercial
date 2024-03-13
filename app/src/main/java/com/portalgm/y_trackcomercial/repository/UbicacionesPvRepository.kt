package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.UbicacionesPVClient
import com.portalgm.y_trackcomercial.data.model.dao.UbicacionesPvDao
import com.portalgm.y_trackcomercial.data.model.models.UbicacionPv
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UbicacionesPvRepository @Inject constructor(
    private val ubicacionesPVClient: UbicacionesPVClient,
    private val sharedPreferences: SharedPreferences,
    private val ubicacionesPvDao:  UbicacionesPvDao
    ) {
    suspend fun fetchUbicacionesPv(): Int {
        return withContext(Dispatchers.IO) {
            val ocrdUbicaciones =  ubicacionesPVClient.getUbicacionesPv(sharedPreferences.getToken().toString())
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