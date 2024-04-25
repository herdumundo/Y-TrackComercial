package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject

class CountRegistrosStockAlmacenUseCase @Inject constructor(
    private val A0_YTV_STOCK_ALMACENRepository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun contar(): Int {
        return A0_YTV_STOCK_ALMACENRepository.getTotalCount()
    }
}