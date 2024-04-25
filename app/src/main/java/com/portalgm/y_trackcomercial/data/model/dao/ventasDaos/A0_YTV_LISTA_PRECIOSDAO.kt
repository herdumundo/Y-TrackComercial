package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_LISTA_PRECIOS_Entity
@Dao
interface A0_YTV_LISTA_PRECIOSDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(ytvVendor: List<A0_YTV_LISTA_PRECIOS_Entity>)

    @Query("delete from A0_YTV_LISTA_PRECIOS")
    suspend fun eliminarTodos()

    @Query("SELECT COUNT(*) FROM A0_YTV_LISTA_PRECIOS")
    fun getCount(): Int

}