package com.example.y_trackcomercial.usecases.marcacionPromotora

import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import javax.inject.Inject

class VerificarCierrePendienteUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    // suspend
    suspend  fun verificarCierrePendiente(): Boolean {
        return permisosVisitasRepository.getCierrePendiente()
    }
}