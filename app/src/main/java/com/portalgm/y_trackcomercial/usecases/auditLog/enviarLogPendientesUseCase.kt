package com.portalgm.y_trackcomercial.usecases.auditLog

import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class EnviarLogPendientesUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend fun enviarLogPendientes(lotes: EnviarLotesDeActividadesRequest) {
        return logRepository.exportarLog(lotes)
    }
}