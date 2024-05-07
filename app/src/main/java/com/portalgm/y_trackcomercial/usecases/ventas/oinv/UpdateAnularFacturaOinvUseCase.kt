package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class UpdateAnularFacturaOinvUseCase @Inject constructor(
    private val repository: OinvRepository
)
{
    suspend fun Update(docEntry: Long) {
        return repository.updateAnularFactura(docEntry)
    }
}