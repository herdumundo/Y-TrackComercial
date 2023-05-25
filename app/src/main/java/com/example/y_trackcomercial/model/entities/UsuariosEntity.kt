package com.example.y_trackcomercial.model.entities


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsuariosEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre_usuario: String,
    val clave: String
)