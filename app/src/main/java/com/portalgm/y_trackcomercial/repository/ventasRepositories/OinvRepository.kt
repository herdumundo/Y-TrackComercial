package com.portalgm.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionOinvApiClient
import com.portalgm.y_trackcomercial.data.api.request.DatosFacturaConLotes
 import com.portalgm.y_trackcomercial.data.api.request.datos_oinv_inv1
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgm.y_trackcomercial.data.model.entities.database.YtrackDatabase
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinvNew
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.DetalleCompleto
import com.portalgm.y_trackcomercial.data.model.models.ventas.procesarDatos
import com.portalgm.y_trackcomercial.data.model.models.ventas.transformarDatosConDetalle
import com.portalgm.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject

class OinvRepository @Inject constructor
    (
    private val oinvPosDao: OINV_DAO,
    private val inv1LotesDao: INV1_LOTES_DAO,
    private val sharedPreferences: SharedPreferences,
    private val database: YtrackDatabase, // Inyecta la instancia de la base de datos
    private val exportacionOinvApiClient: ExportacionOinvApiClient // Paso 1: Agregar el ApiClient al constructor

) {
    suspend fun getAllOinvPosWithDetails(docEntry:Long) = oinvPosDao.getOinvPosWithDetails(docEntry)


    suspend fun getOinvInv1Lotes() = oinvPosDao.getOinvInv1Lotes()

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
        idOrdenVenta:String
    ):Long {
        // La lógica de transacción iría aquí.
      return  database.insertarVentaCompleta(oinv, inv1List, inv1LotesList, idOrdenVenta)
    }

    suspend fun getAllOinvPosByDate(fecha:String) = oinvPosDao.getOinvPosByDate(fecha)

    suspend fun updateAnularFactura(docEntry: Long)  {
        oinvPosDao.updateAnularFactura(docEntry)
    }/*

    suspend fun getAllLotesMovimientosOinv(): List<DatosFacturaConLotes> {
        val facturasDetalles = oinvPosDao.getAllFacturasPendientesExportar()
        val facturasAgrupadas = facturasDetalles.groupBy { it.docEntry }

        return facturasAgrupadas.map { (docEntry, detalles) ->
            val factura = detalles.first() // Toma el primer elemento, asumiendo que todos son iguales por docEntry
            DatosFacturaConLotes(
                datos_oinv_inv1 = datos_oinv_inv1(
                    whsCode = factura.whsCode,
                    slpCode = factura.slpCode,
                    series = factura.series,
                    taxCode = factura.taxCode,
                    docEntry = factura.docEntry,
                    docEntryPedido = factura.docEntryPedido,
                    timbrado = factura.timbrado,
                    cardCode = factura.cardCode,
                    lineNumbCardCode = factura.lineNumbCardCode,
                    idVisita = factura.idVisita,
                    numAtCard = factura.numAtCard,
                    cdc = factura.cdc,
                    xml = factura.xml,
                    qr = factura.qr,
                    anulado = factura.anulado,
                    contado = factura.contado,
                    estado = factura.estado,
                    createdAt = factura.createdAt,
                    updatedAt = factura.updatedAt,
                    totalFacturaIvaIncluido = factura.totalFacturaIvaIncluido,
                    lineNum = factura.lineNum,
                    itemCode = factura.itemCode,
                    itemName = factura.itemName,
                    quantity = factura.quantity,
                    totalLineaIvaIncluido = factura.totalLineaIvaIncluido,
                    precioUnitIvaIncluido = factura.precioUnitIvaIncluido
                ),
                lotes = inv1LotesDao.getLotesPorDocEntry(docEntry)
            )
        }
    }
*/


    /*
    suspend fun getAllLotesMovimientosOinv(): List<datos_oinv_inv1> {
        val lotesDeMovimientosEntity = oinvPosDao.getAllFacturasPendientesExportar()
        return lotesDeMovimientosEntity.map { entity ->
            datos_oinv_inv1(
                whsCode=entity.whsCode,//INV1
                slpCode=entity.slpCode,//OINV
                series=entity.series,//OINV
                taxCode=entity.taxCode,//OINV
                docEntry=entity.docEntry,//OINV
                docEntryPedido=entity.docEntryPedido,//OINV
                timbrado = entity.timbrado,//OINV
                cardCode =entity.cardCode ,//OINV
                lineNumbCardCode =entity.lineNumbCardCode,//OINV
                idVisita=entity.idVisita,//OINV
                numAtCard=entity.numAtCard,//OINV
                cdc=entity.cdc,//OINV
                xml=entity.xml,//OINV
                qr=entity.qr,//OINV
                anulado=entity.anulado,//OINV
                contado=entity.contado,//OINV
                estado=entity.estado,//OINV
                createdAt=entity.createdAt,//OINV
                updatedAt=entity.updatedAt,//OINV
                totalFacturaIvaIncluido=entity.totalFacturaIvaIncluido,//OINV
                lineNum=entity.lineNum,//INV1
                itemCode=entity.itemCode,//INV1
                itemName=entity.itemName,//INV1
                quantity=entity.quantity,//INV1
                totalLineaIvaIncluido=entity.totalLineaIvaIncluido,//INV1
                precioUnitIvaIncluido=entity.precioUnitIvaIncluido//INV1
            )
        }
    }
*/
    suspend fun getAllCountPendientesExportar(): Int {
      return  oinvPosDao.getOinvCountPendientes()
    }
/*
    suspend fun exportarDatos(lotes:  LotesMovimientosOinv) {
        try {
            Log.d("Exportacion",lotes.toString())
            val apiResponse = exportacionOinvApiClient.uploadData(
                lotes,
                sharedPreferences.getToken().toString()
            )
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
             if (apiResponse.tipo == 0) {
             /* val idsLoteActual = lotes.lotesDemovimientos[0]..map { it.docEntry!! }
               oinvPosDao.updateExportadoCerradoPorLotes(idsLoteActual)*/
            }
         } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }*/


    suspend fun exportarDatos(lotes:  DatosMovimientosOinv) {
        try {
            Log.d("Exportacion",lotes.toString())
          /*  val apiResponse = exportacionOinvApiClient.uploadData(
                lotes,
                sharedPreferences.getToken().toString()
            )*/
            val apiResponse = exportacionOinvApiClient.uploadData(
                lotes,
                sharedPreferences.getToken().toString()
            )
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                /* val idsLoteActual = lotes.lotesDemovimientos[0]..map { it.docEntry!! }
                  oinvPosDao.updateExportadoCerradoPorLotes(idsLoteActual)*/
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }
}