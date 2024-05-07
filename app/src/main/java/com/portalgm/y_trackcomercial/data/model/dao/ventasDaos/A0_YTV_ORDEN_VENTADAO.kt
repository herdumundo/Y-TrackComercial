package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_ORDEN_VENTA_Entity
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaDetItem

@Dao
interface A0_YTV_ORDEN_VENTADAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAll(ytv: List<A0_YTV_ORDEN_VENTA_Entity>)
    @Query("delete from A0_YTV_ORDEN_VENTA")
    suspend fun eliminarTodos()
    @Query("SELECT COUNT(*) FROM A0_YTV_ORDEN_VENTA")
    fun getCount(): Int

    @Query("select  distinct docNum,docDate,lineNum as lineNumbCardCode,groupNum, cardCode,shipToCode,docDueDate,pymntGroup,slpName,licTradNum from a0_ytv_orden_venta where estado='P' /*idCliente in (select idOcrd from visitas where pendienteSincro='N')*/")
    suspend fun getOrdenVentaCab(): List<OrdenVentaCabItem>
    @Query("select  distinct docNum,docDate,lineNum as lineNumbCardCode,groupNum,cardCode,shipToCode,docDueDate,pymntGroup,slpName,licTradNum from a0_ytv_orden_venta where docNum=:docNum")
    suspend fun getOrdenVentaCabById(docNum: Int): List<OrdenVentaCabItem>

    @Query(" select  id,lineNumDet,licTradNum,itemCode,itemName,quantity,priceAfVAT from a0_ytv_orden_venta where docNum=:docNum")
    suspend fun getOrdenVentaDet(docNum:Int): List<OrdenVentaDetItem>

    @Transaction
    @Query("UPDATE A0_YTV_ORDEN_VENTA SET estado='C' WHERE docEntry=:idOrdenVenta  " )
    suspend fun  updateCerrado(idOrdenVenta:String)


}