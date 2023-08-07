package com.example.y_trackcomercial.usecases.exportacionVisitas

import com.example.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.example.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class EnviarVisitasPendientesUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun enviarVisitasPendientes(lotesVisitas: EnviarVisitasRequest) {
        return visitasRepository.exportarVisitas(lotesVisitas)
    }
}