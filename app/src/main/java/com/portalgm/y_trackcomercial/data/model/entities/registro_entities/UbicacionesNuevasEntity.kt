package com.portalgm.y_trackcomercial.data.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nuevas_ubicaciones")
data class UbicacionesNuevasEntity(
    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "idOcrd")
    val idOcrd: String,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "idUsuario")
    val idUsuario: Int,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "createdAt")
    var createdAt: String,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "latitudPV")
    var latitudPV: Double,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "longitudPV")
    var longitudPV: Double,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "estado")
    var estado: String,
)