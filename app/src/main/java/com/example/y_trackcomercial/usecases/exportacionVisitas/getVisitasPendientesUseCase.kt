package com.example.y_trackcomercial.usecases.exportacionVisitas

import com.example.y_trackcomercial.model.models.lotesDeVisitas
import com.example.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetVisitasPendientesUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getVisitasPendientes(): List<lotesDeVisitas> {
        return visitasRepository.getAllLotesVisitas()
    }
}