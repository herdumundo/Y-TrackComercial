package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.A0_YTV_VENDEDORClient
import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_VENDEDORDAO
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosFactura
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class A0_YtvVentasRepository @Inject constructor(
    private val A0_YTV_VENDEDORClient: A0_YTV_VENDEDORClient,
    private val A0_YTV_VENDEDORDao: A0_YTV_VENDEDORDAO,
    private val sharedPreferences: SharedPreferences,
) {
    suspend fun fetchA0_YTV_VENDEDOR(): Int {
        return withContext(Dispatchers.IO) {
            val datos = A0_YTV_VENDEDORClient.getVENDEDOR( sharedPreferences.getToken().toString())

            Log.i("MensajeYtrack",datos.toString())
            A0_YTV_VENDEDORDao.eliminarTodos()
            A0_YTV_VENDEDORDao.insertAll(datos)
            return@withContext getTotalCount()
        }
    }
//GHHHHHH
    fun getTotalCount():  Int  {
        return A0_YTV_VENDEDORDao.getCount()
    }
    suspend fun getDatosFactura(): List<A0_YTV_VENDEDOR_Entity> = A0_YTV_VENDEDORDao.getDatosFactura()

    suspend fun exportarDatos(lotes: nroFacturaPendiente) {
        try {
            val apiResponse = exportacionOinvApiClient.uploadData(
                lotes,
                sharedPreferences.getToken().toString()
            )
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }
}