package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.LotesListasApiClient
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LotesListasRepository @Inject constructor(
    private val customerService: LotesListasApiClient,
    private val sharedPreferences: SharedPreferences,
    private val lotesListasDao: com.portalgm.y_trackcomercial.data.model.dao.LotesListasDao
) {

    suspend fun fetchLotesListas(): Int {
        return withContext(Dispatchers.IO) {
            val customers = customerService.getListasLotes(sharedPreferences.getToken().toString())
            lotesListasDao.insertAllLotesListas(customers)
            return@withContext getListasLotesCount()
        }
    }

    fun getListasLotesCount(): Int = lotesListasDao.getLotesListasCount()

    fun getLotesListasByItemCode(itemCode: String): List<com.portalgm.y_trackcomercial.data.model.models.LotesItem> =
        lotesListasDao.getLotesListasByItemCode(itemCode)

 }
