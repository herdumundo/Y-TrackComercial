package com.portalgm.y_trackcomercial.usecases.newPass

import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgm.y_trackcomercial.data.api.request.NuevoPass
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject


class ExportarNewPassPendientesUseCase  @Inject constructor(
    private val newPassRepository: NewPassRepository
) {
    suspend fun enviarPendientes(lotes: NuevoPass) {
        return newPassRepository.exportarDatos(lotes)
    }
}