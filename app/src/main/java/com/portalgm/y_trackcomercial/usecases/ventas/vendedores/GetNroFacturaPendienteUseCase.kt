package com.portalgm.y_trackcomercial.usecases.ventas.vendedores

import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosFactura
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject


class GetNroFacturaPendienteUseCase @Inject constructor(
    private val repository: A0_YtvVentasRepository
)
{
    suspend fun Obtener(): nroFacturaPendiente {
        return repository.getNroFacturaPendiente()
    }
}