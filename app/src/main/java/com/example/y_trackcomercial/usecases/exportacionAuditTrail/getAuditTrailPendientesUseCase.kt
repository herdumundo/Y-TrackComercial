package com.example.y_trackcomercial.usecases.exportacionAuditTrail

import com.example.y_trackcomercial.model.models.lotesDeAuditoriaTrail
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetAuditTrailPendienteUseCase @Inject constructor(
private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun getAuditTrailPendientes(): List<lotesDeAuditoriaTrail> {
        return auditTrailRepository.getAllLotesAuditTrail()
    }
}