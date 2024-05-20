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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    suspend fun insertAll(ytv: List<A0_YTV_ORDEN_VENTA_Entity>)
    @Query("delete from A0_YTV_ORDEN_VENTA WHERE estado='P'")
    suspend fun eliminarTodos()
    @Query("SELECT COUNT(*) FROM A0_YTV_ORDEN_VENTA")
    fun getCount(): Int

    @Query("select  distinct docNum,docDate,lineNum as lineNumbCardCode,groupNum, cardCode,shipToCode," +
            "substr(datetime(substr(docDueDate, 1, 19)), 9, 2) || '/' ||\n" +
            "    substr(datetime(substr(docDueDate, 1, 19)), 6, 2) || '/' ||\n" +
            "    substr(datetime(substr(docDueDate, 1, 19)), 1, 4) AS   docDueDate,pymntGroup,A0_YTV_ORDEN_VENTA.slpName,licTradNum " +
            "   from a0_ytv_orden_venta INNER JOIN A0_YTV_VENDEDOR ON A0_YTV_ORDEN_VENTA.slpCode=A0_YTV_VENDEDOR.slpcode where A0_YTV_ORDEN_VENTA.estado='P' order by  datetime(substr(docDueDate, 1, 19)) desc/*idCliente in (select idOcrd from visitas where pendienteSincro='N')*/")
    suspend fun getOrdenVentaCab(): List<OrdenVentaCabItem>
    @Query("select  distinct docNum,docDate,lineNum as lineNumbCardCode,groupNum,cardCode,shipToCode,docDueDate,pymntGroup,slpName,licTradNum from a0_ytv_orden_venta where docNum=:docNum")
    suspend fun getOrdenVentaCabById(docNum: Int): List<OrdenVentaCabItem>

    @Query(" select  id,lineNumDet,licTradNum,itemCode,itemName,quantity,priceAfVAT," +
            "case unitMsr when  'Unidad'then quantity  when  'Docena' then  quantity*12  when  'Plancha' then  quantity*30   else quantity end as quantityUnidad," +
            "case when  unitMsr='Unidad'then 'Paquete' else unitMsr end as unitMsr from a0_ytv_orden_venta where docNum=:docNum")
    suspend fun getOrdenVentaDet(docNum:Int): List<OrdenVentaDetItem>

    @Transaction
    @Query("UPDATE A0_YTV_ORDEN_VENTA SET estado='C' WHERE docEntry=:idOrdenVenta  " )
    suspend fun  updateCerrado(idOrdenVenta:String)


}