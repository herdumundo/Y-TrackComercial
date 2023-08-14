package com.ytrack.y_trackcomercial.usecases.ubicacionesPv

import com.ytrack.y_trackcomercial.repository.UbicacionesPvRepository
import javax.inject.Inject

class GetUbicacionesPvCountUseCase @Inject constructor(
    private val ubicacionesPvRepository: UbicacionesPvRepository
) {
      fun getUbicacionesPvCount(): Int {
        return ubicacionesPvRepository.getUbicacionesPvCount()
    }
}