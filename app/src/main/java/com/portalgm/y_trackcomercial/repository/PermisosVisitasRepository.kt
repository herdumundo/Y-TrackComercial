package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.PermisosVisitasApiClient
import javax.inject.Inject
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.portalgm.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
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
            val calendar = Calendar.getInstance()
            var token=""
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

            // Verificar si es sábado (7) o domingo (1)
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
            {
                token=sharedPreferences.getToken().toString()
                token = token.removePrefix("Bearer ")
            }
            else
            {
                token= permisosVisitasDao.getToken(tipoPermiso)
            }
            val algorithm = Algorithm.HMAC256("1tZPe7SN1")
            val fechaExpiracion: Date = JWT.require(algorithm).build().verify(token).expiresAt
            val fechaActual = Date()

            fechaExpiracion.after(fechaActual) // Devuelve true si la fecha de expiración es posterior a la fecha actual, de lo contrario, devuelve false
        } catch (e: Exception) {
            e.printStackTrace()
            // true // El token es inválido
              false
        }
    }

    suspend fun getPermisoCierreVisitaInventarioOk(): Boolean = permisosVisitasDao.getPermisoCierreVisitaInventarioOk() != 0

    suspend fun getCierrePendiente(): Boolean = permisosVisitasDao.getCierrePendiente() != 0

}