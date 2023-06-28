package com.example.y_trackcomercial.repository

import android.util.Log
import com.example.y_trackcomercial.data.network.LotesListasApiClient
import com.example.y_trackcomercial.model.dao.LotesListasDao
import javax.inject.Inject


class LotesListasRepository @Inject constructor(
    private val customerService: LotesListasApiClient,
    private val lotesListasDao: LotesListasDao
    ) {
     suspend fun fetchLotesListas(progressCallback: (Float) -> Unit) {
        try {
            val customers = customerService.getListasLotes()
            lotesListasDao.insertAllLotesListas(customers)
            progressCallback(100f)
        } catch (e: Exception) {
            println("Error al obtener los lotes listas: ${e.message}")
            Log.i("Mensaje",e.message.toString())
        }
    }

    fun getListasLotesCount():  Int  {
        return lotesListasDao.getLotesListasCount()
    }
}