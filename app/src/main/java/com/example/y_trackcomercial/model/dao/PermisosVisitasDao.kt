package com.example.y_trackcomercial.model.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.y_trackcomercial.model.entities.PermisosVisitasEntity

@Dao
interface PermisosVisitasDao {

    @Insert
    suspend fun insertPermisoVisita(permisos: List<PermisosVisitasEntity>)

 }
