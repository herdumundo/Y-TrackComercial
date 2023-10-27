package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.LotesListasApiClient
import com.portalgm.y_trackcomercial.data.model.entities.LotesListasEntity
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgm.y_trackcomercial.data.model.models.LotesItem
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
     return try {
         withContext(Dispatchers.IO) {
             val customers = customerService.getListasLotes(sharedPreferences.getToken().toString())
             lotesListasDao.insertAllLotesListas(customers)
             return@withContext getListasLotesCount()
         }
     } catch (e: Exception) {
         // Maneja la excepción aquí
         e.printStackTrace() // Puedes imprimir el seguimiento de la excepción o realizar otras acciones
         -1 // Retorna un valor de error, o cualquier otro valor que indique un error
     }
 }

    suspend fun insertLotesListas(list:List<LotesListasEntity>){
        lotesListasDao.getDeleteLotesLista()
        lotesListasDao.insertAllLotesListas(list)
    }
    fun getListasLotesCount(): Int = lotesListasDao.getLotesListasCount()

    fun getLotesListasByItemCode(itemCode: String): List<LotesItem> =
        lotesListasDao.getLotesListasByItemCode(itemCode)

 }
