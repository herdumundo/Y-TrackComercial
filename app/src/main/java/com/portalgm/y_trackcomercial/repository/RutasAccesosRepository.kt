package com.portalgm.y_trackcomercial.repository

import com.portalgm.y_trackcomercial.data.api.RutasAccesos
import javax.inject.Inject


class RutasAccesosRepository @Inject constructor(
    private val rutasAccesosDao: com.portalgm.y_trackcomercial.data.model.dao.RutasAccesosDao
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
    private fun convertToRutasAccesosEntity(rutasAccesos: RutasAccesos): com.portalgm.y_trackcomercial.data.model.entities.RutasAccesosEntity {
        return com.portalgm.y_trackcomercial.data.model.entities.RutasAccesosEntity(
            id = rutasAccesos.id,
            idPermisoModulo = rutasAccesos.idPermisoModulo,
            name = rutasAccesos.name,
            icono = rutasAccesos.icono,
            ruta = rutasAccesos.ruta
        )
    }

    fun getAllRutasAccesos(): List<com.portalgm.y_trackcomercial.data.model.entities.RutasAccesosEntity> {
        return rutasAccesosDao.getAllRutasaccesos()
    }
    fun getRutasAccesosCount():  Int  {
        return rutasAccesosDao.getRutasAccesosCount()
    }

    suspend fun deleteAndInsertAllRutasAccesos(rutasAccesos: List<RutasAccesos>) {
        try {
            rutasAccesosDao.deleteAllRutasaccesos() // Borra todos los registros existentes

            val rutasAccesosEntities = rutasAccesos.map { rutasAcceso ->
                convertToRutasAccesosEntity(rutasAcceso)
            }
            rutasAccesosDao.insertAllRutasAccesos(rutasAccesosEntities) // Inserta los nuevos registros

        } catch (e: Exception) {

        }
    }


}