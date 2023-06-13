package com.example.y_trackcomercial.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "RutasAccesos",
)
data class RutasAccesosEntity(
    @PrimaryKey
    val id: Int,
    val idPermisoModulo: Int,
    val name: String,
    val icono: String,
    val ruta:String
)
