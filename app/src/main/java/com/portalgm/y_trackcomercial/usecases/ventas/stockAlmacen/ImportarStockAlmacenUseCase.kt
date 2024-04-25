package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject

class ImportarStockAlmacenUseCase @Inject constructor(
    private val A0_YTV_STOCK_ALMACENRepository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun Importar(): Int {
        return A0_YTV_STOCK_ALMACENRepository.sincronizarApi()
    }
}