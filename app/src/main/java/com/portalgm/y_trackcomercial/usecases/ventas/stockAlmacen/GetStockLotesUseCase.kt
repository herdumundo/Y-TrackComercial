package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject


class GetStockLotesUseCase @Inject constructor(
    private val repository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun Obtener(): List<DatosDetalleLotes> {
        return repository.getStockLotes()
    }
}