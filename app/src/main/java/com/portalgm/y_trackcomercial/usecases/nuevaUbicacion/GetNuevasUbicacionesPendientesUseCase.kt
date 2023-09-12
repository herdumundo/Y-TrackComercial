package com.portalgm.y_trackcomercial.usecases.nuevaUbicacion

import com.portalgm.y_trackcomercial.data.api.request.lotesDeUbicacionesNuevas
import com.portalgm.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class GetNuevasUbicacionesPendientesUseCase  @Inject constructor(
    private val NuevaUbicacionRepository: NuevaUbicacionRepository
) {

    suspend  fun GetPendientes(): List<lotesDeUbicacionesNuevas> {
        return NuevaUbicacionRepository.getAllLotesNuevasUbicaciones()
    }
}