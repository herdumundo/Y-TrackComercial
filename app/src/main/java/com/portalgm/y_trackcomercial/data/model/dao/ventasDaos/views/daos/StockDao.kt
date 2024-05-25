package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.daos

import androidx.room.Dao
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.dataviews.V_STOCK_ALMACEN
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStock
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStockPriceList

@Dao
interface StockDao {
    @Query("SELECT * FROM V_STOCK_ALMACEN where ItemCode in (:itemCodes)")
    suspend fun getStockViewByItemCodes(itemCodes: List<String>): List<V_STOCK_ALMACEN>

    @Query("SELECT * FROM V_STOCK_ALMACEN where quantiy  > 0 order by itemName, quantiy desc")
    suspend fun getStockView(): List<V_STOCK_ALMACEN>

    @Query(" select itemCode,itemName,sum(quantiy) as quantity  from V_STOCK_ALMACEN group by itemCode,itemName")
    suspend fun getStockItemCode(): List<DatosItemCodesStock>

    @Query("select T0.itemCode,T0.itemName,sum(T0.quantiy) as quantity,T1.price,T1.priceList, um as unitMsr ,T1.baseQty  " +
            " from V_STOCK_ALMACEN T0 INNER JOIN A0_YTV_LISTA_PRECIOS T1 ON T0.itemCode=T1.itemCode " +
            "   where priceList in (select ListNum from ocrd where id in (select idOcrd from visitas where pendienteSincro='N'))  and um in ('Paquete','Docena') group by T0.itemCode,T0.itemName, T1.price,T1.priceList, um ")
    suspend fun getStockItemCodeWithPriceList(): List<DatosItemCodesStockPriceList>

    @Query("    SELECT count(T0.itemCode) as existe  " +
            "   FROM V_STOCK_ALMACEN T0 " +
            "   INNER JOIN A0_YTV_LISTA_PRECIOS T1 ON T0.itemCode=T1.itemCode where priceList in (select ListNum from ocrd where id in (select idOcrd from visitas where pendienteSincro='N'))  and T1.um='Plancha' and T0.itemCode=:itemCode")
    suspend fun getStockExistenciaPlanchaByItemCode(itemCode:String): Int


    @Query("  select T0.itemCode  ,T0.itemName,sum(T0.quantiy) as quantity,T1.price,T1.priceList, um as unitMsr ,T1.baseQty   " +
            " FROM V_STOCK_ALMACEN T0 INNER JOIN A0_YTV_LISTA_PRECIOS T1 ON T0.itemCode=T1.itemCode " +
            "   WHERE priceList in (select ListNum from ocrd where id in (select idOcrd from visitas where pendienteSincro='N')) and um=:um and t0.itemCode=:itemCode   group by T0.itemCode,T0.itemName, T1.price,T1.priceList, um")
    suspend fun getItemUmedidaCambio(itemCode:String,um:String): List<DatosItemCodesStockPriceList>




}