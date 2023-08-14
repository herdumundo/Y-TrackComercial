package com.ytrack.y_trackcomercial.repository

import com.ytrack.y_trackcomercial.data.api.LotesListasApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LotesListasRepository @Inject constructor(
    private val customerService: LotesListasApiClient,
    private val lotesListasDao: com.ytrack.y_trackcomercial.data.model.dao.LotesListasDao
) {

    suspend fun fetchLotesListas(): Int {
        return withContext(Dispatchers.IO) {
            val customers = customerService.getListasLotes()
            lotesListasDao.insertAllLotesListas(customers)
            return@withContext getListasLotesCount()
        }
    }

    fun getListasLotesCount(): Int = lotesListasDao.getLotesListasCount()

    fun getLotesListasByItemCode(itemCode: String): List<com.ytrack.y_trackcomercial.data.model.models.LotesItem> =
        lotesListasDao.getLotesListasByItemCode(itemCode)

 }
