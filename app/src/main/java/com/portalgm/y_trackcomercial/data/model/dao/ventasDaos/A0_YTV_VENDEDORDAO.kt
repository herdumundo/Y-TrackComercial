package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.google.gson.annotations.SerializedName
import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosFactura
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaDetItem

@Dao
interface A0_YTV_VENDEDORDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    suspend fun insertAll(ytvVendor: List<A0_YTV_VENDEDOR_Entity>)

    @Query("delete from A0_YTV_VENDEDOR where estado='C'")
    suspend fun eliminarTodos()

    @Query("SELECT COUNT(*) FROM A0_YTV_VENDEDOR")
    fun getCount(): Int
    @Query("  SELECT  slpcode   , slpname ,  U_DEPOSITO , IdUsuario , U_SERIEFACT , seriesname ,  Remark ,  u_ci ,  u_ayudante ,  nombre_ayudante ,  u_esta ,  u_pemi , printf('%07d', CAST(ult_nro_fact AS INTEGER) + 1) AS ult_nro_fact ,  U_nro_autorizacion ,  U_TimbradoNro, U_fecha_autoriz_timb,  U_FechaVto FROM A0_YTV_VENDEDOR ")
    suspend fun getDatosFactura(): List<A0_YTV_VENDEDOR_Entity>


    @Query("  SELECT  printf('%07d', CAST(ult_nro_fact AS INTEGER) + 1) AS nuevo_nro_fact FROM A0_YTV_VENDEDOR ")
    suspend fun getNuevoNumeroFactura(): String

    @Transaction
    @Query("UPDATE A0_YTV_VENDEDOR SET  ult_nro_fact=printf('%07d', CAST(ult_nro_fact AS INTEGER) + 1)  " )
    suspend fun  updateNumeroFactura()
    @Transaction
    @Query("UPDATE A0_YTV_VENDEDOR SET estado='P'  " )
    suspend fun  updateEstadoPendiente()

    @Query("select slpCode,ult_nro_fact from A0_YTV_VENDEDOR where estado='P' ")
    suspend fun  nroFacturaPendiente():List<nroFacturaPendiente>


}