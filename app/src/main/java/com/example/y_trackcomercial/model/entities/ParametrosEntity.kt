package com.example.y_trackcomercial.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PARAMETROS")
data class ParametrosEntity(
    @PrimaryKey
    
    val id: Int,
    val codigo: String,
    val valor: String,
    )