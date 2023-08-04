package com.example.y_trackcomercial.usecases.auditLog

import com.example.y_trackcomercial.model.models.EnviarLotesDeActividadesRequest
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class EnviarLogPendientesUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend fun enviarLogPendientes(lotes: EnviarLotesDeActividadesRequest) {
        return logRepository.exportarLog(lotes)
    }
}