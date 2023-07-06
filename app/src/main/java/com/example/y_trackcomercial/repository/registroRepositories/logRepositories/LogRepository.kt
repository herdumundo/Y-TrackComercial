package com.example.y_trackcomercial.repository.registroRepositories.logRepositories

import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.LogDao
import com.example.y_trackcomercial.model.entities.logs.LogEntity
import javax.inject.Inject

class LogRepository @Inject constructor
    (private val logDao: LogDao) {
    suspend fun insertLog(log: LogEntity): Long {
        return logDao.insertLog(log)
    }
}
