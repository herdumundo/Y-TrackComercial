package com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail

import com.portalgm.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class EnviarAuditTrailPendientesUseCase @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun enviarAuditTrailPendientes(lotes: EnviarAuditoriaTrailRequest) {
        return auditTrailRepository.exportarAuditTrail(lotes)
    }
}