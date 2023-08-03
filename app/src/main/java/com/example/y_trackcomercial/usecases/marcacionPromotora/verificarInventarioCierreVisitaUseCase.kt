package com.example.y_trackcomercial.usecases.marcacionPromotora

import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import com.example.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class VerificarInventarioCierreVisitaUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    // suspend
    suspend  fun verificarInventarioExistente(): Boolean {
        return permisosVisitasRepository.getPermisoCierreVisitaInventarioOk()
    }
}