package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.PermisosVisitasApiClient
import javax.inject.Inject
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date


class PermisosVisitasRepository @Inject constructor(
    private val permisosVisitasApiClient: PermisosVisitasApiClient,
    private val sharedPreferences: SharedPreferences,
    private val permisosVisitasDao: com.portalgm.y_trackcomercial.data.model.dao.PermisosVisitasDao,

    ) {

    suspend fun fetchPermisosVisitas(idUsuario: Int): Int {
        return withContext(Dispatchers.IO) {
            val permisos = permisosVisitasApiClient.getPermisosVisitas(idUsuario,sharedPreferences.getToken().toString())
            permisosVisitasDao.eliminarPermisosVisitas()
            permisosVisitasDao.insertPermisoVisita(permisos)
            return@withContext getCountPermisoVisita()
        }
    }

    fun getCountPermisoVisita(): Int = permisosVisitasDao.getPermisoVisitaCount()

    suspend fun verificarPermisoVisita(tipoPermiso: String): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256("1tZPe7SN1")

            val fechaExpiracion: Date = JWT.require(algorithm).build().verify(permisosVisitasDao.getToken(tipoPermiso)).expiresAt
            val fechaActual = Date()

            fechaExpiracion.after(fechaActual) // Devuelve true si la fecha de expiración es posterior a la fecha actual, de lo contrario, devuelve false
        } catch (e: Exception) {
            e.printStackTrace()
            false // El token es inválido
        }
    }

    suspend fun getPermisoCierreVisitaInventarioOk(): Boolean = permisosVisitasDao.getPermisoCierreVisitaInventarioOk() != 0

    suspend fun getCierrePendiente(): Boolean = permisosVisitasDao.getCierrePendiente() != 0

}