package com.portalgm.y_trackcomercial.data.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.api.request.lotesDeUbicacionesNuevas
import com.portalgm.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity

@Dao
interface UbicacionesNuevasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNuevaUbicacion(nuevaUbicacion: UbicacionesNuevasEntity): Long

    @Query("SELECT COUNT(*) FROM nuevas_ubicaciones where estado='P'")
    suspend fun getCountPendientes(): Int


    @Query( "select id,idOcrd,idUsuario,strftime('%Y-%m-%d %H:%M:%S', createdAt)  as createdAt ,latitudPV as latitud,longitudPV as longitud from nuevas_ubicaciones where estado='P'"  )
    suspend fun getAllNuevasUbicacionesExportar(): List<lotesDeUbicacionesNuevas>


    @Query("UPDATE nuevas_ubicaciones SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )
}