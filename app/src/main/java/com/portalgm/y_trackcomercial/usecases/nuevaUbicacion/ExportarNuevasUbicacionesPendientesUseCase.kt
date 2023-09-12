package com.portalgm.y_trackcomercial.usecases.nuevaUbicacion

import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class ExportarNuevasUbicacionesPendientesUseCase  @Inject constructor(
    private val nuevaUbicacionRepository: NuevaUbicacionRepository
) {
    suspend fun enviarPendientes(lotes: EnviarLotesDeUbicacionesNuevasRequest) {
        return nuevaUbicacionRepository.exportarMovimientos(lotes)
    }
}