package com.example.y_trackcomercial.data.model.entities

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
    val tipoPermiso: String// SI ES POR VISITA TARDIA, SI ES POR MARCAR FUERA DEL PUNTO.
)