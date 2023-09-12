package com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail

import com.portalgm.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetAuditTrailPendienteUseCase @Inject constructor(
private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun getAuditTrailPendientes(): List<lotesDeAuditoriaTrail> {
        return auditTrailRepository.getAllLotesAuditTrail()
    }
}