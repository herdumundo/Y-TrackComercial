package com.portalgm.y_trackcomercial.usecases.ventas.vendedores

import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import javax.inject.Inject

class CountRegistrosVendedoresUseCase @Inject constructor(
    private val A0_YtvVentasRepository: A0_YtvVentasRepository
)
{
    suspend fun contar(): Int {
        return A0_YtvVentasRepository.getTotalCount()
    }
}