package com.ytrack.y_trackcomercial.usecases.exportacionVisitas

import com.ytrack.y_trackcomercial.data.api.request.lotesDeVisitas
import com.ytrack.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetVisitasPendientesUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getVisitasPendientes(): List<lotesDeVisitas> {
        return visitasRepository.getAllLotesVisitas()
    }
}