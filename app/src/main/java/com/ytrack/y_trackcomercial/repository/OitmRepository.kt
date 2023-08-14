package com.ytrack.y_trackcomercial.repository

import com.ytrack.y_trackcomercial.data.api.OitmClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OitmRepository @Inject constructor(
    private val oitmClient: OitmClient,
    private val oitmDao: com.ytrack.y_trackcomercial.data.model.dao.OitmDao
    ) {



    suspend fun fetchOitm(): Int {
        return withContext(Dispatchers.IO) {
            val Oitm = oitmClient.getOITM()
            oitmDao.insertAllOitm(Oitm)
            return@withContext getCountOitm()
        }
    }
    fun getCountOitm(): Int = oitmDao.getOitmCount()

    fun getOitm(): List<com.ytrack.y_trackcomercial.data.model.models.OitmItem> = oitmDao.getOitm()


    fun getOitmByPuntoVentaOpen(): List<com.ytrack.y_trackcomercial.data.model.models.OitmItem> = oitmDao.getOitmByPuntoVentaAbierto()



}