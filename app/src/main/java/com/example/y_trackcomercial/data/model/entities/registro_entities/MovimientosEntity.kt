package com.example.y_trackcomercial.data.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*** Tipos de movimientos
 *   1	Inventario
 *   2	Sugerencia Venta
 *   3	Solicitud de devolución
 *   4	Competencia
 * */
@Entity(tableName = "MOVIMIENTOS")
data class MovimientosEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,

    @NonNull
    val idUsuario: Int,//OK

    @NonNull
    val userName: String,//OK

    @NonNull
    val idTipoMov: Int,////OK

    @NonNull
    @ColumnInfo(name = "itemCode")
    var itemCode: String,//OK

    @ColumnInfo(name = "codebar")
    var codebar: String,//OK

    var createdAt: String,//OK

    @ColumnInfo(name = "createdAtLong")
    var createdAtLong: Long,//OK

    @NonNull
    var ubicacion: String,//OK

    @NonNull
    var itemName: String,//OK

    @NonNull
    var cantidad: Int,//OK

    @NonNull
    var lote: String,//OK

    @NonNull
    val idVisitas: Long,

    var loteLargo: String,

    val loteCorto: String,

    val obs: String,
    @ColumnInfo(name = "estado")
    val estado: String,


    )

