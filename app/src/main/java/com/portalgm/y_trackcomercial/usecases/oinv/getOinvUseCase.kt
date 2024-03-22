package com.portalgm.y_trackcomercial.usecases.oinv

 import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
 import com.portalgm.y_trackcomercial.repository.registroRepositories.OinvRepository
 import javax.inject.Inject

class GetOinvUseCase @Inject constructor(
    private val OinvRepository:OinvRepository
) {
    suspend fun execute(): List<OinvPosWithDetails> {
        return OinvRepository.getAllOinvPosWithDetails()
    }
}