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

    @Query("SELECT token FROM PERMISOS_VISITAS   where idEstado=1 LIMIT 1")
    suspend fun getToken(): String



    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertPermisoVisita(permisos: List<PermisosVisitasEntity>)

 }
