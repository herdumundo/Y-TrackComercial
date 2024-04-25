package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.A0_YTV_ORDEN_VENTAClient
import com.portalgm.y_trackcomercial.data.api.A0_YTV_STOCK_ALMACENClient
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_ORDEN_VENTADAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_STOCK_ALMACENDAO
import com.portalgm.y_trackcomercial.data.model.models.OitmItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaDetItem
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class A0_YTV_ORDEN_VENTARepository @Inject constructor(
    private val A0_YTV_ORDEN_VENTAClient: A0_YTV_ORDEN_VENTAClient,
    private val A0_YTV_ORDEN_VENTADAO: A0_YTV_ORDEN_VENTADAO,
    private val sharedPreferences: SharedPreferences,
) {
    suspend fun sincronizarApi(): Int {
        return withContext(Dispatchers.IO) {
            val datos = A0_YTV_ORDEN_VENTAClient.getDatos( sharedPreferences.getToken().toString())
            Log.i("MensajeYtrack",datos.toString())
            A0_YTV_ORDEN_VENTADAO.eliminarTodos()
            A0_YTV_ORDEN_VENTADAO.insertAll(datos)
            return@withContext getTotalCount()
        }
    }
    fun getTotalCount():  Int  {
        return A0_YTV_ORDEN_VENTADAO.getCount()
    }


    suspend fun getOrdenVentaCab(): List<OrdenVentaCabItem> = A0_YTV_ORDEN_VENTADAO.getOrdenVentaCab()

    suspend fun getOrdenVentaDet(docNum:Int): List<OrdenVentaDetItem> = A0_YTV_ORDEN_VENTADAO.getOrdenVentaDet(docNum)

    suspend fun getOrdenVentaCabById(docNum:Int): List<OrdenVentaCabItem> = A0_YTV_ORDEN_VENTADAO.getOrdenVentaCabById(docNum)


}