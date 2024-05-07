package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.data.api.request.datos_oinv_inv1
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosMovimientosOinv
import com.portalgm.y_trackcomercial.data.model.models.ventas.DetalleCompleto
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class GetOinvPendientesExportarUseCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    /*suspend  fun getPendientes():  List<OinvPosWithDetails> {
        return OinvRepository.getOinvInv1Lotes()
    }*/

    suspend  fun getPendientes(): DatosMovimientosOinv  {
        return OinvRepository.getTest()
    }

}