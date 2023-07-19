package com.example.y_trackcomercial.usecases.ubicacionesPv

import com.example.y_trackcomercial.model.models.UbicacionPv
import com.example.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class GetUbicacionesPvUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
    fun getUbicaciones(): List<UbicacionPv> = ubicacionesPvRepository.getUbicaciones()
}