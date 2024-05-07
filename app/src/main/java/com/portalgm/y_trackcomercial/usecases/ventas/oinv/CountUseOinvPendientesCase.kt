package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class CountUseOinvPendientesCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend  fun CountPendientes(): Int {
        return OinvRepository.getAllCountPendientesExportar()
    }
}