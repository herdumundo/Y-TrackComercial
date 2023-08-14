package com.ytrack.y_trackcomercial.usecases.exportacionVisitas

import com.ytrack.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.ytrack.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class EnviarVisitasPendientesUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun enviarVisitasPendientes(lotesVisitas: EnviarVisitasRequest) {
        return visitasRepository.exportarVisitas(lotesVisitas)
    }
}