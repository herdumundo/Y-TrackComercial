package com.portalgm.y_trackcomercial.usecases.ventas.oinv

import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import javax.inject.Inject

class UpdateFirmaOinvUseCase @Inject constructor(
    private val repository: OinvRepository
)
{
    suspend fun Update(qr: String,xml:String,cdc:String,docEntry: Long) {
        return repository.updateFirmaOinvPos(qr,xml,cdc,docEntry)
    }
}