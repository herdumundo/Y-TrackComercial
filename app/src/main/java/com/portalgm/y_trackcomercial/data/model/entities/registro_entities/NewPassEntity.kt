package com.portalgm.y_trackcomercial.data.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "NewPass")
data class NewPassEntity(
    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "idUsuario")
    val idUsuario: Int,//OK

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "newPass")
    val newPass: String,//OK

    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @ColumnInfo(name = "estado")
    val estado: String,
    )

