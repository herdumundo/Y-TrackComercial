package com.ytrack.y_trackcomercial.usecases.exportacionAuditTrail

import com.ytrack.y_trackcomercial.data.api.request.lotesDeAuditoriaTrail
import com.ytrack.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import javax.inject.Inject

class GetAuditTrailPendienteUseCase @Inject constructor(
private val auditTrailRepository: AuditTrailRepository
) {
    suspend  fun getAuditTrailPendientes(): List<lotesDeAuditoriaTrail> {
        return auditTrailRepository.getAllLotesAuditTrail()
    }
}