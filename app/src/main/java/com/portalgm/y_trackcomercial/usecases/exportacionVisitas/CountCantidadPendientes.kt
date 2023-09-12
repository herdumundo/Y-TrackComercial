package com.portalgm.y_trackcomercial.usecases.exportacionVisitas

 import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class CountCantidadPendientes  @Inject constructor(
    private val visitasRepository: VisitasRepository
) {
    suspend  fun ContarCantidadPendientes(): Int {
        return visitasRepository.getCantidadPendientesExportar()
    }
}