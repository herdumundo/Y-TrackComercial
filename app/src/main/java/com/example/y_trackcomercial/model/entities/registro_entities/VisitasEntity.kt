package com.example.y_trackcomercial.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@Entity(tableName = "VISITAS")
data class VisitasEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,

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

    val idA: Int?,

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
    var tarde : String,

    @NonNull
    @ColumnInfo(name = "idTurno")
    var idTurno : Int,

    var tipoCierre : String // FORZADO O NORMAL

)