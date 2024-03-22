package com.portalgm.y_trackcomercial.repository.registroRepositories

import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.OinvPosDao
import javax.inject.Inject

class OinvRepository @Inject constructor
    (
    private val oinvPosDao: OinvPosDao

) {
    suspend fun getAllOinvPosWithDetails() = oinvPosDao.getOinvPosWithDetails()
 }