package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionOinvApiClient
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgm.y_trackcomercial.data.model.entities.database.YtrackDatabase
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.procesarDatos
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class OinvRepository @Inject constructor
    (
    private val oinvPosDao: OINV_DAO,
    private val sharedPreferences: SharedPreferences,
    private val database: YtrackDatabase, // Inyecta la instancia de la base de datos
    private val exportacionOinvApiClient: ExportacionOinvApiClient // Paso 1: Agregar el ApiClient al constructor

) {
    suspend fun getAllOinvPosWithDetails(docEntry:Long) = oinvPosDao.getOinvPosWithDetails(docEntry)

    suspend fun getTest(): DatosMovimientosOinv {
        return procesarDatos(oinvPosDao.getOinvInv1Lotes())
    }
    suspend fun insertOinvPos(oinv: OINV_POS): Long {
        return oinvPosDao.insertOinv(oinv)
    }
    suspend fun updateFirmaOinvPos(qr: String,xml:String,cdc:String,docEntry: Long)  {
          oinvPosDao.updateFirmaFactura(qr,xml,cdc,docEntry)
    }
    suspend fun insertarVentaCompleta(
        oinv: OINV_POS,
        inv1List: List<INV1_POS>,
        inv1LotesList: List<INV1_LOTES_POS>,
        idOrdenVenta:String,
        ultimoNroFactura:String
    ):Long {
        // La lógica de transacción iría aquí.
      return  database.insertarVentaCompleta(oinv, inv1List, inv1LotesList, idOrdenVenta,ultimoNroFactura)
    }

    suspend fun getAllOinvPosByDate(fecha:String) = oinvPosDao.getOinvPosByDate(fecha)

    suspend fun updateAnularFactura(docEntry: Long)  {
        oinvPosDao.updateAnularFactura(docEntry)
    }
    suspend fun getAllCountPendientesExportar(): Int {
      return  oinvPosDao.getOinvCountPendientes()
    }

    suspend fun exportarDatos(lotes:  DatosMovimientosOinv) {
        try {
             val apiResponse = exportacionOinvApiClient.uploadData(
                lotes,
                sharedPreferences.getToken().toString()
            )
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {

                oinvPosDao.updateExportadoCerradoPorLotes(apiResponse.docEntries)
                // Preparar los docEntries para la confirmación
                val confirmacionData = ConfirmacionData(docEntries = apiResponse.docEntries)

                // Hacer la segunda llamada a la API para confirmar los docEntries
                val confirmacionResponse = exportacionOinvApiClient.confirmarFacturas(
                    confirmacionData,
                    sharedPreferences.getToken().toString()
                )
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }
}


// Clase de datos para la confirmación
data class ConfirmacionData(
    val docEntries: List<String>
)
