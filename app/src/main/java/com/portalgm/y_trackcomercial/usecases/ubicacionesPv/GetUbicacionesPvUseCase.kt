package com.portalgm.y_trackcomercial.usecases.ubicacionesPv

import com.portalgm.y_trackcomercial.data.model.models.UbicacionPv
import com.portalgm.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class GetUbicacionesPvUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
    fun getUbicaciones(): List<UbicacionPv> = ubicacionesPvRepository.getUbicaciones()
}