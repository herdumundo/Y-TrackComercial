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

    @Query("SELECT IDa FROM VISITAS WHERE pendienteSincro='N' LIMIT 1  ") //TIENE QUE DEVOLVER EL ID DE LA VISITA DE APERTURA, ES "N" PORQUE DEBERIA DE EXISTIR SOLO UN REGISTRO CON ESE VALOR
    suspend fun getIdVisitaActiva(): Long

    @Query("SELECT count(*)+1 FROM VISITAS WHERE estadoVisita='A' and strftime('%d/%m/%Y', createdAt)=strftime('%d/%m/%Y',  datetime('now') )") //TIENE QUE DEVOLVER EL ID DE LA VISITA DE APERTURA, ES "N" PORQUE DEBERIA DE EXISTIR SOLO UN REGISTRO CON ESE VALOR
    suspend fun getSecuenciaVisita(): Int

    @Query("UPDATE visitas SET createdAt = :createdAt, createdAtLong = :createdAtLong, latitudUsuario = :latitud,tipoCierre=:tipoCierre, longitudUsuario = :longitud, porcentajeBateria = :porcentajeBateria, distanciaMetros = :distanciaMetros, pendienteSincro=:pendienteSincro, exportado= :exportado, tipoRegistro = :tipoRegistro WHERE id = :visitaId")
    suspend fun updateVisita(
        visitaId: Long?,
        createdAt: String,
        createdAtLong: Long,
        latitud: Double,
        longitud: Double,
        porcentajeBateria: Int,
        distanciaMetros: Int?,
        pendienteSincro : String?,
        exportado : Boolean,
        tipoRegistro: String,
        tipoCierre: String,
    )
    @Query("SELECT COUNT(*) FROM visitas WHERE pendienteSincro='N'")
    fun getCantidadRegistrosPendientes(): LiveData<Int>

    @Query("SELECT ocrdName FROM visitas WHERE pendienteSincro='N'")
    fun getOcrdPendiente(): LiveData<String>



    @Query("SELECT count(*) FROM visitas WHERE strftime('%d/%m/%Y', createdAt) = :fecha AND idTurno = :idTurno and estadoVisita='A'")
    suspend fun getEsPrimeraVisita(fecha: String, idTurno: Int): Int

/** SECTOR DE EXPORTACIONES. */


    @Query("SELECT id , idUsuario,idOcrd,strftime('%Y-%m-%d %H:%M:%S', createdAt)  as createdAt,createdAtLong,latitudUsuario,longitudUsuario,latitudPV,longitudPV,porcentajeBateria, idA,idRol,tipoRegistro,  distanciaMetros,ocrdName,pendienteSincro,estadoVisita,llegadaTardia,idTurno,tipoCierre,rol,  exportado, secuencia  FROM visitas where pendienteSincro='P'  ")
    suspend fun getVisitasExportaciones(): List<VisitasEntity>


    @Query("SELECT COUNT(*) FROM visitas WHERE pendienteSincro='P'")
    fun getCantidadPendientesExportar():  Int


    @Query("UPDATE visitas SET pendienteSincro='C' where pendienteSincro='P'")
    suspend fun updateExportadoCerrado(  )


}