package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgm.y_trackcomercial.data.api.request.balanceOcrd
import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity

@Dao
interface A0_YTV_VENDEDORDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    suspend fun insertAll(ytvVendor: List<A0_YTV_VENDEDOR_Entity>)

    @Query("delete from A0_YTV_VENDEDOR where estado='C'")
    suspend fun eliminarTodos()

    @Query("SELECT COUNT(*) FROM A0_YTV_VENDEDOR")
    fun getCount(): Int
    @Query("  SELECT  slpcode   , slpname ,  U_DEPOSITO , IdUsuario , U_SERIEFACT , seriesname ,  " +
            "Remark ,  u_ci ,  u_ayudante ,  nombre_ayudante ,  u_esta ,  u_pemi , printf('%07d', CAST(ult_nro_fact AS INTEGER) + 1) AS ult_nro_fact ,  " +
            "U_nro_autorizacion ,  U_TimbradoNro, U_fecha_autoriz_timb,  U_FechaVto, estado FROM A0_YTV_VENDEDOR ")
    suspend fun getDatosFactura(): List<A0_YTV_VENDEDOR_Entity>


    @Query("  SELECT  printf('%07d', CAST(ult_nro_fact AS INTEGER) + 1) AS nuevo_nro_fact FROM A0_YTV_VENDEDOR ")
    suspend fun getNuevoNumeroFactura(): String

    @Transaction
    @Query("UPDATE A0_YTV_VENDEDOR SET  ult_nro_fact=:ultimoNroFactura  " )
    suspend fun  updateNumeroFactura(ultimoNroFactura: String)
    @Transaction
    @Query("UPDATE A0_YTV_VENDEDOR SET estado='P'  " )
    suspend fun  updateEstadoPendiente()

    @Transaction
    @Query("UPDATE A0_YTV_VENDEDOR SET estado='C'  " )
    suspend fun  updateEstadoCerrado()


    @Query("select slpcode,ult_nro_fact,'' as cardCode from A0_YTV_VENDEDOR where estado='P' ")
    suspend fun  nroFacturaPendiente():nroFacturaPendiente
    @Query("select slpcode,ult_nro_fact,:cardCode as cardCode from A0_YTV_VENDEDOR ")
    suspend fun  nroFacturaUltimo(cardCode:String):nroFacturaPendiente

    @Query("select count(slpcode)from A0_YTV_VENDEDOR where estado='P' ")
    suspend fun  nroFacturaPendienteCount():Int
    @Query("select ifnull(sum(totalIvaIncluido),0) as totalIvaIncluido  from OINV_POS WHERE  docEntrySap is null and estado<>'E' and cardCode=:cardCode")
    suspend fun  getTotalFacturaLocal(cardCode:String):Long

    @Query("select Balance,CreditDisp,CreditLine  from ocrd WHERE cardCode=:cardCode")
    suspend fun  getBalanceOcrdLocal(cardCode:String):balanceOcrd

}