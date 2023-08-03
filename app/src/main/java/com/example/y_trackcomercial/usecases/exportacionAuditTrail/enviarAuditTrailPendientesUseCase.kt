package com.example.y_trackcomercial.usecases.exportacionAuditTrail

import com.example.y_trackcomercial.model.models.EnviarAuditoriaTrailRequest
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class EnviarAuditTrailPendientesUseCase @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun enviarAuditTrailPendientes(lotes: EnviarAuditoriaTrailRequest) {
        return auditTrailRepository.exportarAuditTrail(lotes)
    }
}