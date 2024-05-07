package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.LotesMovimientosOinvNew
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.DetalleCompleto
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ExportarOinvPendientesUseCase @Inject constructor(
    private val repository: OinvRepository
) {
  /*  suspend  fun exportarDatos(lotes:  LotesMovimientosOinv) {
        return repository.exportarDatos(lotes)
    }
*/
    suspend  fun exportarDatos(lotes:   DatosMovimientosOinv ) {
        return repository.exportarDatos(lotes)
    }
}