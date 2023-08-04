package com.example.y_trackcomercial.usecases.inventario

import com.example.y_trackcomercial.model.models.EnviarLotesDeMovimientosRequest
import com.example.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class EnviarMovimientoPendientesUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    suspend fun enviarPendientes(lotes: EnviarLotesDeMovimientosRequest) {
        return movimientosRepository.exportarMovimientos(lotes)
    }
}