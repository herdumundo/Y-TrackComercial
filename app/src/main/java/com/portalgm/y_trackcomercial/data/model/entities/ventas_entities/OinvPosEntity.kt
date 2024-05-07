package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

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
    @ColumnInfo(name = "docEntryPedido")    var docEntryPedido: String?,
    @ColumnInfo(name = "idVisita")          var idVisita: Long,
    @ColumnInfo(name = "lineNumbCardCode")  var lineNumbCardCode: Int,
    @ColumnInfo(name = "cardCode")          var cardCode: String?,
    @ColumnInfo(name = "licTradNum")        var licTradNum: String?,
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
    @ColumnInfo(name = "iva")               var iva: String?,
    @ColumnInfo(name = "vigenciaTimbrado")  var vigenciaTimbrado: String?,
    @ColumnInfo(name = "tipoContribuyente")  var tipoContribuyente: String?, /*1 si es persona fisica y 2 si es persona juridica  */
    @ColumnInfo(name = "ci")                var ci: String?, /* si es persona fisica informar  y  persona juridica no informar */
    @ColumnInfo(name = "address")           var address: String?,
    @ColumnInfo(name = "correo")            var correo: String?,
    @ColumnInfo(name = "contado")           var contado: String?,// 1= SI, 2= NO
    @ColumnInfo(name = "condicion")         var condicion: String?,
    @ColumnInfo(name = "totalIvaIncluido")  var totalIvaIncluido: String?,
    @ColumnInfo(name = "anulado")           var anulado: String= "N",
    @ColumnInfo(name = "estado")            var estado: String?

)