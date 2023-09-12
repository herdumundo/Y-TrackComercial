package com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail

 import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
 import javax.inject.Inject

class CountAuditTrailUseCase  @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
       suspend  fun CountPendientesExportacion(): Int {
        return auditTrailRepository.getAuditTrailCount()
    }
}