package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_STOCK_ALMACEN_Entity
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes

@Dao
interface A0_YTV_STOCK_ALMACENDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(ytvVendor: List<A0_YTV_STOCK_ALMACEN_Entity>)

    @Query("delete from A0_YTV_STOCK_ALMACEN")
    suspend fun eliminarTodos()

    @Query("SELECT COUNT(*) FROM A0_YTV_STOCK_ALMACEN")
    fun getCount(): Int
    @Query("SELECT itemName,ItemCode,distNumber,loteLargo,WhsCode,Quantity FROM A0_YTV_STOCK_ALMACEN  WHERE itemCode  IN (:itemCodes)  ")
    suspend fun getDetalleLotes(itemCodes: List<String>): List<DatosDetalleLotes>

}