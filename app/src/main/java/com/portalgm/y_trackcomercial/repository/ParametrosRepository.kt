package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.ParametrosClient
import com.portalgm.y_trackcomercial.data.model.dao.ParametrosDao
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ParametrosRepository @Inject constructor(
    private val parametrosClient: ParametrosClient,
    private val parametrosDao: ParametrosDao,
    private val sharedPreferences: SharedPreferences,
    ) {
    suspend fun fetchParametros()  {
        return withContext(Dispatchers.IO) {
            val customers = parametrosClient.getParametros( sharedPreferences.getToken().toString())
            parametrosDao.insertAllParametros(customers)
         }
    }
    suspend fun getTimerGpsHilo1():  Int  {
        return parametrosDao.getParametroHiloGps1()
    }
}