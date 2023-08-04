package com.example.y_trackcomercial.usecases.inventario

import com.example.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject


class CountMovimientoUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend  fun CountPendientes(): Int {
        return movimientosRepository.getCount()
    }
}