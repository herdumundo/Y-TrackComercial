package com.portalgm.y_trackcomercial.data.model.entities.logs

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

    @ColumnInfo(name = "estado")
    val estado: String,
    @NonNull
    val bateria: Int ,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "idVisita")
    val idVisita: Long ,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "distanciaPV")
    val distanciaPV: Int,


    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "tiempo")
    val tiempo: Int,


    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "tipoRegistro")
    val tipoRegistro: String



)
