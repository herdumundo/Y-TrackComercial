package com.portalgm.y_trackcomercial.usecases.permisoVisita

import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import javax.inject.Inject

class ImportarPermisoVisitaUseCase @Inject constructor(
    private val permisosVisitasRepository: PermisosVisitasRepository
) {
    suspend fun importarPermisoVisita(idUsuario: Int): Int {
        return try {
            permisosVisitasRepository.fetchPermisosVisitas(idUsuario)
        } catch (e: Exception) {
            // Maneja la excepción aquí
            e.printStackTrace() // Puedes imprimir el seguimiento de la excepción o realizar otras acciones
            -1 // Retorna un valor de error, o cualquier otro valor que indique un error
        }
    }

}