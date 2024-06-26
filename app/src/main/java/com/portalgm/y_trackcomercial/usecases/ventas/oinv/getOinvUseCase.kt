package com.portalgm.y_trackcomercial.usecases.ventas.oinv

 import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
 import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
 import javax.inject.Inject

class GetOinvUseCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend fun execute(docEntry:Long): List<OinvPosWithDetails> {
        return OinvRepository.getAllOinvPosWithDetails(docEntry)
    }
}