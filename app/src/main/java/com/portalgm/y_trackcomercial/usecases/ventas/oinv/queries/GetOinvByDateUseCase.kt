package com.portalgm.y_trackcomercial.usecases.ventas.oinv.queries

 import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
 import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
 import javax.inject.Inject

class GetOinvByDateUseCase @Inject constructor(
    private val OinvRepository: OinvRepository
) {
    suspend fun execute(fecha:String): List<OinvPosWithDetails> {
        return OinvRepository.getAllOinvPosByDate(fecha)
    }
}