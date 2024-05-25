package com.portalgm.y_trackcomercial.usecases.ventas.oinv.exports
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ExportarOinvCancelacionesPendientesUseCase @Inject constructor(
    private val repository: OinvRepository
) {

    suspend  fun exportarDatos() {
        return repository.exportarDatosCancelacion()
    }
}