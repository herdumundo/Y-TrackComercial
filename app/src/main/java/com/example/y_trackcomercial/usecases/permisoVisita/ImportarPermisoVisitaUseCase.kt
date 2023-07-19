package com.example.y_trackcomercial.usecases.permisoVisita

import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import javax.inject.Inject

class ImportarPermisoVisitaUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    suspend fun importarPermisoVisita(idUsuario: Int): Int {
        return permisosVisitasRepository.fetchPermisosVisitas(idUsuario)
    }
}