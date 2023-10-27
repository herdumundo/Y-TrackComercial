package com.portalgm.y_trackcomercial.usecases.parametros

import com.portalgm.y_trackcomercial.repository.ParametrosRepository
import javax.inject.Inject

class GetTimerGpsHilo1UseCase @Inject constructor(
    private val parametrosRepository: ParametrosRepository
) {
       suspend fun getInt() : Int {
     return    parametrosRepository.getTimerGpsHilo1()
    }

}