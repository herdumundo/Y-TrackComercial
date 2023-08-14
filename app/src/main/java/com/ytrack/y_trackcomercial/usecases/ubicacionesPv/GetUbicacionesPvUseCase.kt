package com.ytrack.y_trackcomercial.usecases.ubicacionesPv

import com.ytrack.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class GetUbicacionesPvUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
    fun getUbicaciones(): List<com.ytrack.y_trackcomercial.data.model.models.UbicacionPv> = ubicacionesPvRepository.getUbicaciones()
}