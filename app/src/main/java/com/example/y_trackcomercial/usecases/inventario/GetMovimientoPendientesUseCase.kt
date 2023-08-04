package com.example.y_trackcomercial.usecases.inventario

import com.example.y_trackcomercial.model.models.lotesDemovimientos
import com.example.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetMovimientoPendientesUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {

    suspend  fun GetPendientes(): List<lotesDemovimientos> {
        return movimientosRepository.getAllLotesMovimientos()
    }
}