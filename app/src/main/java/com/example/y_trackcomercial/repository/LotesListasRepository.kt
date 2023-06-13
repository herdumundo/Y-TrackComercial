package com.example.y_trackcomercial.repository

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
            // Manejar la excepción
        }
    }
}