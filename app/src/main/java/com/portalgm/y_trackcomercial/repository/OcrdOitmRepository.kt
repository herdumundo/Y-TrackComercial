package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.OcrdOitmClient
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OcrdOitmRepository @Inject constructor(
    private val ocrdoitmClient: OcrdOitmClient,
    private val sharedPreferences: SharedPreferences,
    private val ocrdOitmDao: com.portalgm.y_trackcomercial.data.model.dao.OcrdOitmDao
    ) {


    suspend fun fetchOcrdOitm(): Int {
        return withContext(Dispatchers.IO) {
            // Borra todos los datos de la tabla antes de insertar nuevos datos
            ocrdOitmDao.deleteAllOcrdOitm()
            val Oitm = ocrdoitmClient.getOcrditem(sharedPreferences.getToken().toString())
            ocrdOitmDao.insertAllOcrdOitm(Oitm)
            return@withContext getCountOcrdOitm()
        }
    }

    fun getCountOcrdOitm(): Int = ocrdOitmDao.getOcrdOitmCount()

 }