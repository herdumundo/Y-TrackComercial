package com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta

import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_ORDEN_VENTARepository
import javax.inject.Inject


class GetOrdenVentaCabByIdUseCase @Inject constructor(
    private val repository: A0_YTV_ORDEN_VENTARepository
)
{
    suspend fun Obtener(docNum:Int): List<OrdenVentaCabItem> {
        return repository.getOrdenVentaCabById(docNum)
    }
}