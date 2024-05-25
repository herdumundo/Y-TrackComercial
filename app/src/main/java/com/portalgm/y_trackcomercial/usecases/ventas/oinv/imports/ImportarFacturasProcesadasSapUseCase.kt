package com.portalgm.y_trackcomercial.usecases.ventas.oinv.imports
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class ImportarFacturasProcesadasSapUseCase @Inject constructor(
    private val repository: OinvRepository
) {

    suspend  fun exportarDatos() {
        return repository.importarFacturasProcesadasDocentries()
    }
}