package com.portalgm.y_trackcomercial.data.model.entities.registro_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
 @Entity(
    tableName = "INV1_POS",
    foreignKeys = [
        ForeignKey(
            entity = OINV_POS::class,
            parentColumns = ["docEntry"],
            childColumns = ["docEntry"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class INV1_POS(
    @PrimaryKey(autoGenerate = true)    var id: Int = 0,
    @ColumnInfo(name = "docEntry")      val docEntry: Long,
    @ColumnInfo(name = "LineNum")       var lineNum: Short,
    @ColumnInfo(name = "ItemCode")      var itemCode: String,
    @ColumnInfo(name = "ItemName")      var itemName: String?,
    @ColumnInfo(name = "WhsCode")       var whsCode: String,
    @ColumnInfo(name = "Quantity")      var quantity: Int,
    @ColumnInfo(name = "PriceAfterVat") var priceAfterVat: Int,
    @ColumnInfo(name = "UoMEntry")      var uomEntry: Int,
    @ColumnInfo(name = "TaxCode")       var taxCode: String?
)