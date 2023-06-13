package com.example.y_trackcomercial.repository

import RutasAccesos
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity
import javax.inject.Inject


class RutasAccesosRepository @Inject constructor(
    private val rutasAccesosDao: RutasAccesosDao
    )
{
    suspend fun fetchRutasAccesos(result: List<RutasAccesos>) {
        try {
             val rutasAccesosEntities = result.map { rutasAcceso ->
                convertToRutasAccesosEntity(rutasAcceso)
            }
            rutasAccesosDao.insertAllRutasAccesos(rutasAccesosEntities)

         } catch (e: Exception) {
            // Manejar la excepción
        }
    }
    // Conversion function to convert RutasAccesos to RutasAccesosEntity
    private fun convertToRutasAccesosEntity(rutasAccesos: RutasAccesos): RutasAccesosEntity {
        return RutasAccesosEntity(
            id = rutasAccesos.id,
            idPermisoModulo = rutasAccesos.idPermisoModulo,
            name = rutasAccesos.name,
            icono = rutasAccesos.icono,
            ruta = rutasAccesos.ruta
        )
    }

    fun getAllRutasAccesos(): List<RutasAccesosEntity> {
        return rutasAccesosDao.getAllRutasaccesos()
    }
    fun getRutasAccesosCount():  Int  {
        return rutasAccesosDao.getRutasAccesosCount()
    }



}