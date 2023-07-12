package com.example.y_trackcomercial.model.dao.registroDaos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity

@Dao
interface VisitasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitas(visita: VisitasEntity): Long

    @Query("SELECT * FROM VISITAS WHERE estadoVisita = :estado LIMIT 1")
    suspend fun getVisitaByEstado(estado: String): VisitasEntity?

    @Query("SELECT * FROM VISITAS WHERE tipoRegistro='A' and estadoVisita = :estado LIMIT 1 ")
    suspend fun getVisitaActiva(estado: String): VisitasEntity?

    @Query("UPDATE visitas SET createdAt = :createdAt, createdAtLong = :createdAtLong, latitudUsuario = :latitud,tipoCierre=:tipoCierre, longitudUsuario = :longitud, porcentajeBateria = :porcentajeBateria, distanciaMetros = :distanciaMetros, pendienteSincro = :pendienteSincro, tipoRegistro = :tipoRegistro WHERE id = :visitaId")
    suspend fun updateVisita(
        visitaId: Int?,
        createdAt: String,
        createdAtLong: Long,
        latitud: Double,
        longitud: Double,
        porcentajeBateria: Int,
        distanciaMetros: Int?,
        pendienteSincro: String?,
        tipoRegistro: String,
        tipoCierre: String,
    )
    @Query("SELECT COUNT(*) FROM visitas WHERE pendienteSincro = 'N'")
    fun getCantidadRegistrosPendientes(): LiveData<Int>

    @Query("SELECT ocrdName FROM visitas WHERE pendienteSincro = 'N'")
    fun getOcrdPendiente(): LiveData<String>



    @Query("SELECT count(*) FROM visitas WHERE strftime('%d/%m/%Y', createdAt) = :fecha AND idTurno = :idTurno")
    suspend fun getEsPrimeraVisita(fecha: String, idTurno: Int): Int

}