package com.example.y_trackcomercial.usecases.exportacionAuditTrail

 import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
 import javax.inject.Inject

class CountAuditTrailUseCase  @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository
) {
       suspend  fun CountPendientesExportacion(): Int {
        return auditTrailRepository.getAuditTrailCount()
    }
}