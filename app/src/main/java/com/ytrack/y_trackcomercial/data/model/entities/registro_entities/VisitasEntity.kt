package com.ytrack.y_trackcomercial.data.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VISITAS")
data class VisitasEntity(

    @PrimaryKey()
    val id: Long,

    @NonNull
    val idUsuario: Int,

    @NonNull
    val idOcrd: String,

    @NonNull
    @ColumnInfo(name = "createdAt")
    var createdAt: String,

    @ColumnInfo(name = "createdAtLong")
    var createdAtLong: Long,

    @NonNull
    var latitudUsuario: Double,

    @NonNull
    var longitudUsuario: Double,

    @NonNull
    var latitudPV: Double,

    @NonNull
    var longitudPV: Double,

    var porcentajeBateria: Int,

    val idA: Long,

    val idRol: Int,

    var tipoRegistro: String,

    var distanciaMetros: Int?,

    var ocrdName: String?,

    @ColumnInfo(name = "pendienteSincro", defaultValue = "P")
    var pendienteSincro: String? = "P",

    @ColumnInfo(name = "estadoVisita", defaultValue = "A")
    val estadoVisita: String? = "A",

    @NonNull
    @ColumnInfo(name = "llegadaTardia")
    var tarde: String,

    @NonNull
    @ColumnInfo(name = "idTurno")
    var idTurno: Int,


    var tipoCierre: String,// FORZADO O NORMAL

    @ColumnInfo(name = "rol", defaultValue = "") // Nueva columna
    var rol: String,

    var exportado: Boolean,

    @NonNull
    @ColumnInfo(name = "secuencia")
    var secuencia: Int,
)