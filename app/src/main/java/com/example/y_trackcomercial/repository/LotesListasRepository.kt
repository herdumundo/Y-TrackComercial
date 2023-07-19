package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.LotesListasApiClient
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.models.LotesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LotesListasRepository @Inject constructor(
    private val customerService: LotesListasApiClient,
    private val lotesListasDao: LotesListasDao
) {

    suspend fun fetchLotesListas(): Int {
        return withContext(Dispatchers.IO) {
            val customers = customerService.getListasLotes()
            lotesListasDao.insertAllLotesListas(customers)
            return@withContext getListasLotesCount()
        }
    }

    fun getListasLotesCount(): Int = lotesListasDao.getLotesListasCount()

    fun getLotesListasByItemCode(itemCode: String): List<LotesItem> =
        lotesListasDao.getLotesListasByItemCode(itemCode)

 }
