package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.daos

import androidx.room.Dao
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.dataviews.V_STOCK_ALMACEN
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStock

@Dao
interface StockDao {
    @Query("SELECT * FROM V_STOCK_ALMACEN where ItemCode in (:itemCodes)")
    suspend fun getStockViewByItemCodes(itemCodes: List<String>): List<V_STOCK_ALMACEN>

    @Query("SELECT * FROM V_STOCK_ALMACEN where quantiy  > 0 order by itemName, quantiy desc")
    suspend fun getStockView(): List<V_STOCK_ALMACEN>

    @Query(" select itemCode,itemName,sum(quantiy) as quantity  from V_STOCK_ALMACEN group by itemCode,itemName")
    suspend fun getStockItemCode(): List<DatosItemCodesStock>



}