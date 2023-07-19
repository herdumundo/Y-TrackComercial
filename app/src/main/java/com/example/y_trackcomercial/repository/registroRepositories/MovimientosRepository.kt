package com.example.y_trackcomercial.repository.registroRepositories

import com.example.y_trackcomercial.model.dao.registroDaos.MovimientosDao
import com.example.y_trackcomercial.model.entities.registro_entities.MovimientosEntity
import com.example.y_trackcomercial.model.models.Lotes
import javax.inject.Inject

class MovimientosRepository @Inject constructor
    (private val movimientosDao: MovimientosDao) {

    suspend fun insertLotesInBulk(lotesList: List<Lotes>) {
        val createdAtLongVar=System.currentTimeMillis()
        val movimientosList = lotesList.map { lote ->
            MovimientosEntity(
                itemCode = lote.ItemName,
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
                createdAtLong = createdAtLongVar,
                idUsuario = lote.idUsuario,
                idTipoMov = lote.tipoMovimiento,
                userName = lote.userName
             )
        }

        movimientosDao.insertAllMovimiento(movimientosList)
    }
}
