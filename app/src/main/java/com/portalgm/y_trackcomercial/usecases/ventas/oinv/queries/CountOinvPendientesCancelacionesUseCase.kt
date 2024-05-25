package com.portalgm.y_trackcomercial.usecases.ventas.oinv.queries

import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class CountOinvPendientesCancelacionesUseCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend  fun CountPendientes(): Int {
        return OinvRepository.getAllCountPendientesExportarCancelaciones()
    }
}