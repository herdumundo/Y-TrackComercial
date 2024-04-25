package com.portalgm.y_trackcomercial.repository.ventasRepositories

import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1LoteDetalle
import javax.inject.Inject
class INV1_LOTES_REPOSITORY @Inject constructor
    (
        private val INV1_LOTES_DAO: INV1_LOTES_DAO
    ) {
    suspend fun insertInv1LoteBulk(lotesList: List<Inv1LoteDetalle>) {
        val movimientosList = lotesList.map { lote ->
            INV1_LOTES_POS(
            itemCode = lote.ItemCode,
            docEntry= lote.docEntry,
            quantity= lote.Quantity.toString(),
            lote =  lote.Lote
            )
        }
        INV1_LOTES_DAO.insertAll(movimientosList)
    }
}