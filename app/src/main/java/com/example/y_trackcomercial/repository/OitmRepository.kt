package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.OitmClient
import com.example.y_trackcomercial.model.dao.OitmDao
import com.example.y_trackcomercial.model.entities.OitmEntity
import com.example.y_trackcomercial.model.models.OitmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OitmRepository @Inject constructor(
    private val oitmClient: OitmClient,
    private val oitmDao: OitmDao
    ) {



    suspend fun fetchOitm(): Int {
        return withContext(Dispatchers.IO) {
            val Oitm = oitmClient.getOITM()
            oitmDao.insertAllOitm(Oitm)
            return@withContext getCountOitm()
        }
    }
    fun getCountOitm(): Int = oitmDao.getOitmCount()

    fun getOitm(): List<OitmItem> = oitmDao.getOitm()


    fun getOitmByPuntoVentaOpen(): List<OitmItem> = oitmDao.getOitmByPuntoVentaAbierto()



}