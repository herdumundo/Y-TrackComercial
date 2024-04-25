package com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_ORDEN_VENTARepository
import javax.inject.Inject

class ImportarOrdenVentaUseCase @Inject constructor(
    private val repository: A0_YTV_ORDEN_VENTARepository
)
{
    suspend fun Importar(): Int {
        return repository.sincronizarApi()
    }
}