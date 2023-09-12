package com.portalgm.y_trackcomercial.usecases.inventario

import com.portalgm.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetMovimientoPendientesUseCase  @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {

    suspend  fun GetPendientes(): List<lotesDemovimientos> {
        return movimientosRepository.getAllLotesMovimientos()
    }
}