package com.example.y_trackcomercial.repository.registroRepositories.logRepositories

import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.example.y_trackcomercial.model.entities.logs.AuditTrailEntity
import javax.inject.Inject

class AuditTrailRepository @Inject constructor
    (private val auditTrailDao: AuditTrailDao) {
    suspend fun insertAuditTrailDao(auditTrailEntity: AuditTrailEntity): Long {
        return auditTrailDao.insertAuditTrailDao(auditTrailEntity)
    }

    fun getAuditTrailCount():  Int  {
        return auditTrailDao.getAuditTrailCount()
    }
}
