package com.portalgm.y_trackcomercial.usecases.visitas

import com.portalgm.y_trackcomercial.data.model.models.LatitudLongitudPVIniciado
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import javax.inject.Inject

class GetDatosVisitaActivaUseCase @Inject constructor(
private val visitasRepository: VisitasRepository
) {
    suspend  fun getDatosVisitaActivaUseCase(): List<LatitudLongitudPVIniciado> {
        return visitasRepository.getDatosVisitaActiva()
    }
}