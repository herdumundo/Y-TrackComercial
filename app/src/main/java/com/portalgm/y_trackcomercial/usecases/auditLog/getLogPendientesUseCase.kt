package com.portalgm.y_trackcomercial.usecases.auditLog

import com.portalgm.y_trackcomercial.data.api.request.lotesDeActividades
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import javax.inject.Inject

class GetLogPendienteUseCase @Inject constructor(
    private val logRepository: LogRepository
) {
    suspend  fun getAuditLogPendientes(): List<lotesDeActividades> {
        return logRepository.getAllLotesAuditLog()
    }
}