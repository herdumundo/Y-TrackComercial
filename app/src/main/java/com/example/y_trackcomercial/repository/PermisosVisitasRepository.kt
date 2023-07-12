package com.example.y_trackcomercial.repository

import com.example.y_trackcomercial.data.network.PermisosVisitasApiClient
import com.example.y_trackcomercial.model.dao.PermisosVisitasDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date


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

    suspend fun verificarPermisoVisita(): Boolean {
        return try {
            val algorithm = Algorithm.HMAC256("1tZPe7SN1")
            val verifier = JWT.require(algorithm).build()
            val jwt = verifier.verify(permisosVisitasDao.getToken("INICIOVISITA"))

            val fechaExpiracion: Date = jwt.expiresAt
            val fechaActual = Date()

            fechaExpiracion.after(fechaActual) // Devuelve true si la fecha de expiración es posterior a la fecha actual, de lo contrario, devuelve false
        } catch (e: Exception) {
            // Manejo de errores
            e.printStackTrace()
            false // El token es inválido
        }
    }



}