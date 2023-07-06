package com.example.y_trackcomercial.model.entities.logs

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AuditTrail")
data class AuditTrailEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? ,

    @ColumnInfo(name = "fecha")
    val fecha: String?,

    @ColumnInfo(name = "fechaLong")
    val fechaLong: Long?,

    @ColumnInfo(name = "idUsuario")
    val usuarioId: Int?,

    @ColumnInfo(name = "latitud")
    val latitud: Double?,

    @ColumnInfo(name = "longitud")
    val longitud: Double?,

    @ColumnInfo(name = "velocidad")
    val velocidad: Double?,
   // @NonNull
    val nombreUsuario: String?,
)
