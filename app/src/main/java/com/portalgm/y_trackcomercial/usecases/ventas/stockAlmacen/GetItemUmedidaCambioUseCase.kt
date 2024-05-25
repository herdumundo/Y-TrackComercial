package com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen

import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStockPriceList
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import javax.inject.Inject
class GetItemUmedidaCambioUseCase @Inject constructor(
    private val repository: A0_YTV_STOCK_ALMACENRepository
)
{
    suspend fun Obtener(itemCode: String, /*priceList: Int,*/um:String): List<DatosItemCodesStockPriceList> {
        return repository.getItemUmedidaCambio(itemCode,  um)
    }
}