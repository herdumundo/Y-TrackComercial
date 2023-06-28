package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.PermisosVisitasApiClient
import com.example.y_trackcomercial.model.dao.PermisosVisitasDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PermisosVisitasRepository @Inject constructor(
    private val permisosVisitasApiClient: PermisosVisitasApiClient,
    private val permisosVisitasDao: PermisosVisitasDao

) {
    suspend fun fetchPermisosVisitas(idUsuario: Int) {
        try {
            val permisos = permisosVisitasApiClient.getPermisosVisitas(idUsuario)
            permisosVisitasDao.insertPermisoVisita(permisos)

        } catch (e: Exception) {
           val dasd=e.toString()
        }
    }


  /*  suspend fun fetchPermisosVisitas(idUsuario: Int) {
        try {
            val response = apiClient.getPermisosVisitas(idUsuario)
            val dd=response

            if (response.isSuccessful) {
                val permisosVisitas = response.body()
                permisosVisitas?.let {
                    withContext(Dispatchers.IO) {
                        permisosVisitasDao.insertPermisoVisita(it)
                    }
                }
            } else {
                // Manejar el error de la respuesta del servicio
            }
        } catch (e: Exception) {
            // Manejar el error de la solicitud de red
        }
    }*/
}