package com.example.y_trackcomercial.repository.registroRepositories

import android.util.Log
import com.example.y_trackcomercial.data.api.exportaciones.ExportacionMovimientosApiClient
import com.example.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.example.y_trackcomercial.data.api.request.lotesDemovimientos
import javax.inject.Inject

class MovimientosRepository @Inject constructor
    (
    private val movimientosDao: com.example.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao,
    private val exportacionMovimientosApiClient: ExportacionMovimientosApiClient
) {

    suspend fun insertLotesInBulk(lotesList: List<com.example.y_trackcomercial.data.model.models.Lotes>) {
        //  val createdAtLongVar=System.currentTimeMillis()
        val movimientosList = lotesList.map { lote ->
            com.example.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity(
                itemCode = lote.itemCode,
                codebar = lote.codeBars,
                ubicacion = lote.ubicacion,
                itemName = lote.ItemName,
                cantidad = lote.Cantidad,
                lote = lote.Lote,
                idVisitas = lote.idVisitas,
                loteLargo = lote.Lote,
                loteCorto = lote.Lote,
                obs = "",
                createdAt = lote.createdAt,
                createdAtLong = lote.createdAtLong,
                idUsuario = lote.idUsuario,
                idTipoMov = lote.tipoMovimiento,
                userName = lote.userName,
                estado = "P"
            )
        }

        movimientosDao.insertAllMovimiento(movimientosList)
    }

    suspend fun getInformeInventario(fecha: String): MutableList<com.example.y_trackcomercial.data.model.models.InformeInventario> =
        movimientosDao.getInformeMovimiento(fecha)

    suspend fun getCount(): Int {
        return movimientosDao.getCountPendientes()
    }


    suspend fun getAllLotesMovimientos(): List<lotesDemovimientos> {
        val lotesDeMovimientosEntity = movimientosDao.getAllMovimientosExportar()
        return lotesDeMovimientosEntity.map { entity ->
            lotesDemovimientos(
                id = entity.id,
                idUsuario = entity.idUsuario,
                userName = entity.userName,
                idTipoMov = entity.idTipoMov,
                itemCode = entity.itemCode,
                codebar = entity.codebar,
                createdAt = entity.createdAt,
                createdLong = entity.createdLong,
                updatedAt = entity.updatedAt,
                ubicacion = entity.ubicacion,
                itemName = entity.itemName,
                cantidad = entity.cantidad,
                lote = entity.lote,
                idVisitas = entity.idVisitas,
                loteLargo = entity.loteLargo,
                loteCorto = entity.loteCorto,
                obs = entity.obs,
            )
        }
    }


    suspend fun exportarMovimientos(lotes: EnviarLotesDeMovimientosRequest) {
        try {
            val apiResponse = exportacionMovimientosApiClient.uploadMovimientosData(lotes)
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                movimientosDao.updateExportadoCerrado()
            }
            Log.i("MensajeTest", apiResponse.msg)
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }
    }
}
