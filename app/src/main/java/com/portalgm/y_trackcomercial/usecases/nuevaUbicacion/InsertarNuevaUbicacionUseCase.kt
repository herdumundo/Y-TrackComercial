package com.portalgm.y_trackcomercial.usecases.nuevaUbicacion


import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import javax.inject.Inject

class InsertarNuevaUbicacionUseCase @Inject constructor(
    private val NuevaUbicacionRepository:NuevaUbicacionRepository
)
{
    suspend fun Insertar(datos: UbicacionesNuevasEntity): Long {
        return NuevaUbicacionRepository.insertNuevaUbicacion(datos)
    }
}


