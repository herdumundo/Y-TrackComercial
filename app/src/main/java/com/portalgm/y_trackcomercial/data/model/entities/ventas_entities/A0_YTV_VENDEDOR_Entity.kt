package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "A0_YTV_VENDEDOR")
data class A0_YTV_VENDEDOR_Entity(
    @PrimaryKey
    @SerializedName( "slpcode")
    val slpcode: Int,
    @SerializedName( "slpname")
    val slpname: String,

    @SerializedName( "U_DEPOSITO")
    val U_DEPOSITO: String?,

    @SerializedName( "IdUsuario")
    val IdUsuario: String,

    @SerializedName( "U_SERIEFACT")
    val U_SERIEFACT: Int?,

    @SerializedName( "seriesname")
    val seriesname: String,

    @SerializedName( "Remark")
    val Remark: String?,

    @SerializedName( "u_ci")
    val u_ci: String?,

    @SerializedName(  "u_ayudante")
    val u_ayudante: String?,

    @SerializedName( "nombre_ayudante")
    val nombre_ayudante: String?,

    @SerializedName( "u_esta")
    val u_esta: String,

    @SerializedName( "u_pemi")
    val u_pemi: String,

    @SerializedName( "ult_nro_fact")
    val ult_nro_fact: String,

    @SerializedName( "U_nro_autorizacion")
    val U_nro_autorizacion: String?,

    @SerializedName( "U_TimbradoNro")
    val U_TimbradoNro: String?,

    @SerializedName( "U_fecha_autoriz_timb")
    val U_fecha_autoriz_timb: String?,

    @SerializedName( "U_FechaVto")
    val U_FechaVto: String?,
    @SerializedName( "estado")
    val estado: String?
)
