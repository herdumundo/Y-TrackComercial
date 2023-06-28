package com.example.y_trackcomercial.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "PERMISOS_VISITAS"
)
data class PermisosVisitasEntity(
    @PrimaryKey
    val id: Int,
    val idUsuario: Int,
    val token: String,
    val createdAt: String,
    val idEstado: String,
)