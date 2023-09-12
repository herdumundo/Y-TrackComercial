package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.OitmClient
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OitmRepository @Inject constructor(
    private val oitmClient: OitmClient,
    private val sharedPreferences: SharedPreferences,
    private val oitmDao: com.portalgm.y_trackcomercial.data.model.dao.OitmDao
    ) {



    suspend fun fetchOitm(): Int {
        return withContext(Dispatchers.IO) {
            val Oitm = oitmClient.getOITM(sharedPreferences.getToken().toString())
            oitmDao.insertAllOitm(Oitm)
            return@withContext getCountOitm()
        }
    }
    fun getCountOitm(): Int = oitmDao.getOitmCount()

    fun getOitm(): List<com.portalgm.y_trackcomercial.data.model.models.OitmItem> = oitmDao.getOitm()


    fun getOitmByPuntoVentaOpen(): List<com.portalgm.y_trackcomercial.data.model.models.OitmItem> = oitmDao.getOitmByPuntoVentaAbierto()



}