package com.portalgm.y_trackcomercial.usecases.exportacionVisitas

import com.portalgm.y_trackcomercial.data.api.request.lotesDeVisitas
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetVisitasPendientesUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getVisitasPendientes(): List<lotesDeVisitas> {
        return visitasRepository.getAllLotesVisitas()
    }
}