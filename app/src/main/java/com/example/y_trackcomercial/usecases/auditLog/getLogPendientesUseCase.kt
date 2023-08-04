package com.example.y_trackcomercial.usecases.auditLog

import com.example.y_trackcomercial.model.models.lotesDeActividades
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class GetLogPendienteUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend  fun getAuditLogPendientes(): List<lotesDeActividades> {
        return logRepository.getAllLotesAuditLog()
    }
}