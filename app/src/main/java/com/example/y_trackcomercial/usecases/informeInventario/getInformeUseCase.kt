package com.example.y_trackcomercial.usecases.informeInventario

import com.example.y_trackcomercial.data.model.models.InformeInventario
import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import com.example.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import javax.inject.Inject


class GetInformeUseCase @Inject constructor(
    private val movimientosRepository: MovimientosRepository
) {
    // suspend
    suspend  fun getInforme(fecha: String): MutableList<com.example.y_trackcomercial.data.model.models.InformeInventario> {
        return movimientosRepository.getInformeInventario(fecha)
    }
}