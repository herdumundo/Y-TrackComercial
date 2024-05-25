package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "A0_YTV_STOCK_ALMACEN")
data class A0_YTV_STOCK_ALMACEN_Entity(
    @PrimaryKey
    @SerializedName( "id")
    val id: String,

    @SerializedName( "ItemCode")
    val itemCode: String?,

    @SerializedName( "ItemName")
    val itemName: String?,

    @SerializedName( "WhsCode")
    val whsCode: String?,

    @SerializedName( "Quantity")
    val quantity: String?,

    @SerializedName( "DistNumber")
    val distNumber: String?,

    @SerializedName( "LoteLargo")
    val loteLargo: String?,

    @SerializedName( "UpdateDate")
    val updateDate: String?,

)
