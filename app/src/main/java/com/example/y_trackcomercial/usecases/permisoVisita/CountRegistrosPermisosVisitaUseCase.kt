package com.example.y_trackcomercial.usecases.permisoVisita

import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import javax.inject.Inject



class CountRegistrosPermisosVisitaUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    fun getCountPermisoVisitaCount(): Int {
        return permisosVisitasRepository.getCountPermisoVisita()
    }
}