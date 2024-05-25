package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStock
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject


class GetStockItemCodeUseCase @Inject constructor(
    private val repository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun Obtener(): List<DatosItemCodesStock> {
        return repository.getStockItemCode()
    }
}