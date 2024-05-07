package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS

@Entity(
    tableName = "INV1_POS",
    foreignKeys = [
        ForeignKey(
            entity = OINV_POS::class,
            parentColumns = ["docEntry"],
            childColumns = ["docEntry"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["docEntry"])]
)
data class INV1_POS(
    @PrimaryKey(autoGenerate = true)        var id: Int = 0,
    @ColumnInfo(name = "docEntry")          var docEntry: Long,
    @ColumnInfo(name = "LineNum")           var lineNum: Int,
    @ColumnInfo(name = "ItemCode")          var itemCode: String,
    @ColumnInfo(name = "ItemName")          var itemName: String?,
    @ColumnInfo(name = "WhsCode")           var whsCode: String,
    @ColumnInfo(name = "Quantity")          var quantity: String,
    @ColumnInfo(name = "PriceAfterVat")     var priceAfterVat: String,/*IVA INCLUIDO PRECION UNITARIO*/
    @ColumnInfo(name = "PrecioUnitSinIva")  var precioUnitSinIva: String,
    @ColumnInfo(name = "PrecioUnitIvaInclu")  var precioUnitIvaInclu: String,
    @ColumnInfo(name = "TotalSinIva")       var totalSinIva: String,
    @ColumnInfo(name = "TotalIva")          var totalIva: String,
    @ColumnInfo(name = "UoMEntry")          var uomEntry: Int,
    @ColumnInfo(name = "TaxCode")           var taxCode: String?

)