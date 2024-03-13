package com.portalgm.y_trackcomercial.usecases.ocrdUbicaciones

import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import javax.inject.Inject


class ImportarOcrdUbicacionesUseCase @Inject constructor(
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository
)
{
    suspend fun Importar(): Int {
        return ocrdUbicacionesRepository.fetchOcrdUbicaciones()
    }
}