package com.portalgm.y_trackcomercial.usecases.marcacionPromotora

import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import javax.inject.Inject

class VerificarInventarioCierreVisitaUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    // suspend
    suspend  fun verificarInventarioExistente(): Boolean {
        return permisosVisitasRepository.getPermisoCierreVisitaInventarioOk()
    }
}