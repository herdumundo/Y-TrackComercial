package com.example.y_trackcomercial.model.entities.logs

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ActivityLog")
data class LogEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @NonNull
    val fecha: String,

    @ColumnInfo(name = "fechaLong")
    var fechaLong: Long,

    @NonNull
    @ColumnInfo(name = "etiqueta") //Una etiqueta o categoría que ayuda a clasificar el tipo de evento registrado, como "Autenticación", "Red", "Base de datos", etc.
    val etiqueta: String,

    @ColumnInfo(name = "mensaje")
    val mensaje: String,

    @NonNull
    val idUsuario: Int,

    @NonNull
    val nombreUsuario: String,

    @NonNull
    val componente: String,// El componente o clase de tu aplicación que generó el evento.
    @NonNull
    val bateria: Int,
    @ColumnInfo(name = "estado")
    @NonNull
    val estado: String
)