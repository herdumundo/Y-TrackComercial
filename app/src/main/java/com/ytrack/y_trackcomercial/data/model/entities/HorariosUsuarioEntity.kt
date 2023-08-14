package com.ytrack.y_trackcomercial.data.model.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "turnosHorarios")
data class HorariosUsuarioEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @NonNull
    val idHorario: Int,

    @NonNull
    val descripcion: String,

    @NonNull
    @ColumnInfo(name = "horaEnt")
    var horaEntrada: String,

    @ColumnInfo(name = "horaSal")
    var horaSalida: String,

    @ColumnInfo(name = "tolEnt")
    @NonNull
    var toleranciaEntrada: String,

    @ColumnInfo(name = "tolSal")
    @NonNull
    var toleranciaSalida: String,

    @ColumnInfo(name = "inicioEnt")
    @NonNull
    var inicioEntrada: String,

    @ColumnInfo(name = "finEnt")
    @NonNull
    var finEntrada: String,

    @ColumnInfo(name = "inicioSal")
    @NonNull
    var inicioSalida: String,

    @ColumnInfo(name = "finSal")
    @NonNull
    var finSalida: String,
    )