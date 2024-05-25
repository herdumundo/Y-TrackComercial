package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "INV1_LOTES_POS",
    foreignKeys = [
        ForeignKey(
            entity = OINV_POS::class,
            parentColumns = ["docEntry"],
            childColumns = ["docEntry"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class INV1_LOTES_POS(
    @PrimaryKey(autoGenerate = true)        var id: Int = 0,
    @ColumnInfo(name = "docEntry")          var docEntry: Long,
    @ColumnInfo(name = "ItemCode")          var itemCode: String,
    @ColumnInfo(name = "Lote")              var lote: String?,
    @ColumnInfo(name = "LoteLargo")         var loteLargo: String?,
    @ColumnInfo(name = "Quantity")          var quantity: String,
    @ColumnInfo(name = "QuantityCalculado") var quantityCalculado: String,
)