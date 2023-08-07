package com.example.y_trackcomercial.usecases.ubicacionesPv

import com.example.y_trackcomercial.data.model.models.UbicacionPv
import com.example.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class GetUbicacionesPvUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
    fun getUbicaciones(): List<com.example.y_trackcomercial.data.model.models.UbicacionPv> = ubicacionesPvRepository.getUbicaciones()
}