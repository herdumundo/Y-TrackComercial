package com.portalgm.y_trackcomercial.usecases.newPass

import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import javax.inject.Inject


class CountNewPassPendienteUseCase  @Inject constructor(
    private val newPassRepository: NewPassRepository
) {
    suspend  fun CountPendientes(): Int {
        return newPassRepository.getCount()
    }
}