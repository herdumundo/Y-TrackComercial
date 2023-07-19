package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.OcrdOitmClient
import com.example.y_trackcomercial.model.dao.OcrdOitmDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdOitmRepository @Inject constructor(
    private val ocrdoitmClient: OcrdOitmClient,
    private val ocrdOitmDao: OcrdOitmDao
    ) {


    suspend fun fetchOcrdOitm(): Int {
        return withContext(Dispatchers.IO) {
            // Borra todos los datos de la tabla antes de insertar nuevos datos
            ocrdOitmDao.deleteAllOcrdOitm()
            val Oitm = ocrdoitmClient.getOcrditem()
            ocrdOitmDao.insertAllOcrdOitm(Oitm)
            return@withContext getCountOcrdOitm()
        }
    }

    fun getCountOcrdOitm(): Int = ocrdOitmDao.getOcrdOitmCount()

 }