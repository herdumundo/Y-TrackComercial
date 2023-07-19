package com.example.y_trackcomercial.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.y_trackcomercial.model.entities.LotesListasEntity
import com.example.y_trackcomercial.model.entities.PermisosVisitasEntity

@Dao
interface PermisosVisitasDao {

    @Query("SELECT token FROM PERMISOS_VISITAS WHERE idEstado = 1 and tipoPermiso=:tipoPermiso ORDER BY id DESC LIMIT 1")
    suspend fun getToken(tipoPermiso : String ): String

    @Query("SELECT count(*) FROM PERMISOS_VISITAS WHERE idEstado = 1 ")
      fun getPermisoVisitaCount(): Int


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertPermisoVisita(permisos: List<PermisosVisitasEntity>)

 }
