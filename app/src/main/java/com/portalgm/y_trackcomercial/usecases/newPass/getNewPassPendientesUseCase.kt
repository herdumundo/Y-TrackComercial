package com.portalgm.y_trackcomercial.usecases.newPass

import com.portalgm.y_trackcomercial.data.api.request.NuevoPass
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import javax.inject.Inject

class GetNewPassPendientesUseCase @Inject constructor(
private val newPassRepository: NewPassRepository
) {
    suspend  fun getPendientes(): NuevoPass {
        return newPassRepository.getAllLotesNuevasUbicaciones()
    }
}