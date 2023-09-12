package com.portalgm.y_trackcomercial.usecases.inventario

import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class CountMovimientoUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend  fun CountPendientes(): Int {
        return movimientosRepository.getCount()
    }
}