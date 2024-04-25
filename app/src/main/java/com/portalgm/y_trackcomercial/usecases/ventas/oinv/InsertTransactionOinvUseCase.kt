package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class InsertTransactionOinvUseCase @Inject constructor(
    private val repository: OinvRepository
){
    suspend fun Insertar(OINV_POS: OINV_POS,INV1_POS: List<INV1_POS>, INV1_LOTES_POS: List<INV1_LOTES_POS>,idOrdenVenta:String):Long  {
        return repository.insertarVentaCompleta(OINV_POS,INV1_POS,INV1_LOTES_POS,idOrdenVenta)
    }
}