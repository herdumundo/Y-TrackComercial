package com.portalgm.y_trackcomercial.data.model.entities.registro_entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
 @Entity(tableName = "OINV_POS")
data class OINV_POS(
    @NonNull // Anotación para indicar que idUsuario no puede ser nulo
    @PrimaryKey
    @ColumnInfo(name = "docEntry")
    val docEntry: Long,
    @ColumnInfo(name = "cardCode")          var cardCode: String?,
    @ColumnInfo(name = "cardName")          var cardName: String?,
    @ColumnInfo(name = "docDate")           var docDate: String?,
    @ColumnInfo(name = "docDueDate")        var docDueDate: String?,
    @ColumnInfo(name = "series")            var series: String?,
    @ColumnInfo(name = "folioNumber")       var folioNumber: Int?,
    @ColumnInfo(name = "numAtCard")         var numAtCard: String?,
    @ColumnInfo(name = "slpCode")           var slpCode: Int?,
    @ColumnInfo(name = "timb")              var timb: String?,
    @ColumnInfo(name = "cdc")               var cdc: String?,
    @ColumnInfo(name = "qr")                var qr: String?,
    @ColumnInfo(name = "xmlFact")           var xmlFact: String?,
    @ColumnInfo(name = "xmlNombre")         var xmlNombre: String?,
    @ColumnInfo(name = "estado")            var estado: String?
)