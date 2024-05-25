package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject

class CountExistenciaStockPlanchaByItemCodeUseCase @Inject constructor(
    private val A0_YTV_STOCK_ALMACENRepository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun existe(itemCode: String ): Int {
        return A0_YTV_STOCK_ALMACENRepository.getStockExistenciaPlanchaByItemCode(itemCode)
    }
}