package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionOinvApiClient
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
    suspend fun updateFirmaOinvPos(
        qr: String,
        xml: String,
        cdc: String,
        docEntry: Long,
        txtSiedi: String
    )  {
          oinvPosDao.updateFirmaFactura(qr,xml,cdc,docEntry,txtSiedi)
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
        val estado= oinvPosDao.getEstadoFacturaById(docEntry)
        if(estado=="C"){
            oinvPosDao.updateAnularFactura(docEntry)
        }
        else{
            oinvPosDao.updateAnularFacturaNoEnviadaAzure(docEntry)
        }
    }
    suspend fun getAllCountPendientesExportar(): Int {   return  oinvPosDao.getOinvCountPendientes()    }
    suspend fun getAllCountPendientesExportarCancelaciones(): Int {   return  oinvPosDao.getOinvCountPendientesCancelaciones()    }
    suspend fun getCountDocentriesNoProcesadosSap(): Int {   return  oinvPosDao.getCountDocentriesNoProcesadosSap()    }



    suspend fun exportarDatos(lotes:  DatosMovimientosOinv) {
        try {
            val apiResponse = exportacionOinvApiClient.uploadData(lotes, sharedPreferences.getToken().toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                oinvPosDao.updateExportadoCerradoPorLotes(apiResponse.docEntries)
                // Preparar los docEntries para la confirmación
                val confirmacionData = ConfirmacionData(docEntries = apiResponse.docEntries)
                // Hacer la segunda llamada a la API para confirmar los docEntries
                exportacionOinvApiClient.confirmarFacturas(confirmacionData,sharedPreferences.getToken().toString())
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }

    suspend fun exportarDatosCancelacion() {
        try {
            val docEntries = oinvPosDao.getOinvCancelacion()
            val confirmacionData = ConfirmacionData(docEntries = docEntries)
            val apiResponse = exportacionOinvApiClient.cancelarFacturas(confirmacionData, sharedPreferences.getToken().toString())
            if (apiResponse.tipo == 0) {
                oinvPosDao.updateExportadoCerradoPorLotes(docEntries)
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }

    suspend fun importarFacturasProcesadasDocentries() {
        try {
            val docEntries = oinvPosDao.getDocentriesNoProcesadosSap()
            val confirmacionData = ConfirmacionData(docEntries = docEntries)
            val apiResponse = exportacionOinvApiClient.confirmarFacturasSap(confirmacionData, sharedPreferences.getToken().toString())

            if (apiResponse.tipo == 0) {
                     apiResponse.docEntries.forEach { entry ->
                        oinvPosDao.updateExportadoCerradoPorLotes(entry.docEntrySap, entry.docEntry)
                    }
                 Log.i("Mensaje", "Actualización exitosa")
            }
        } catch (e: Exception) {
            Log.e("Mensaje", "Error: ${e.message}", e)
        }
    }

}

data class ConfirmacionData(
    val docEntries: List<Long>
)

