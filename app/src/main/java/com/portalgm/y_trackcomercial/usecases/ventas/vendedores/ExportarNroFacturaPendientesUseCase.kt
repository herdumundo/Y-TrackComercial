package com.portalgm.y_trackcomercial.usecases.ventas.vendedores

import com.portalgm.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinvNew
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.DetalleCompleto
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ExportarNroFacturaPendientesUseCase @Inject constructor(
    private val repository: A0_YtvVentasRepository
) {

    suspend  fun exportarDatos(lotes: nroFacturaPendiente) {
        return repository.exportarDatos(lotes)
    }
}