package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.A0_YTV_STOCK_ALMACENClient
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_STOCK_ALMACENDAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.daos.StockDao
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
 import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class A0_YTV_STOCK_ALMACENRepository @Inject constructor(
    private val A0_YTV_STOCK_ALMACENClient: A0_YTV_STOCK_ALMACENClient,
    private val A0_YTV_STOCK_ALMACENDAO: A0_YTV_STOCK_ALMACENDAO,
    private val stockDao: StockDao,

    private val sharedPreferences: SharedPreferences,
) {
    suspend fun sincronizarApi(): Int {
        return withContext(Dispatchers.IO) {
            val datos = A0_YTV_STOCK_ALMACENClient.getDatos( sharedPreferences.getToken().toString())
            A0_YTV_STOCK_ALMACENDAO.eliminarTodos()
            A0_YTV_STOCK_ALMACENDAO.insertAll(datos)
            return@withContext getTotalCount()
        }
    }

    fun getTotalCount():  Int  {
        return A0_YTV_STOCK_ALMACENDAO.getCount()
    }
  //  suspend fun getDetalleLotesByItemCodeGroup(itemCodes:List<String>): List<DatosDetalleLotes> = stockDao.getStockView(itemCodes)

    suspend fun getDetalleLotesByItemCodeGroup(itemCodes: List<String>): List<DatosDetalleLotes> {
        val stockViewList = stockDao.getStockViewByItemCodes(itemCodes)
        return stockViewList.map { stockView ->
            DatosDetalleLotes(
                itemName = stockView.itemName,
                itemCode = stockView.itemCode,
                distNumber = stockView.distNumber,
                loteLargo = stockView.loteLargo,
                whsCode = stockView.whsCode,
                quantity = stockView.quantiy?.toString()
            )
        }
    }

    suspend fun getStockLotes(): List<DatosDetalleLotes> {
        val stockViewList = stockDao.getStockView()
        return stockViewList.map { stockView ->
            DatosDetalleLotes(
                itemName = stockView.itemName,
                itemCode = stockView.itemCode,
                distNumber = stockView.distNumber,
                loteLargo = stockView.loteLargo,
                whsCode = stockView.whsCode,
                quantity = stockView.quantiy?.toString()
            )
        }
    }
}