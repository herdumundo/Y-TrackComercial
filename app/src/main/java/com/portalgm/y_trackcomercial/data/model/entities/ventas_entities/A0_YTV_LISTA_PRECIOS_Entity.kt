package com.portalgm.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "A0_YTV_LISTA_PRECIOS")
data class A0_YTV_LISTA_PRECIOS_Entity(
    @PrimaryKey
    @SerializedName( "id")
    val id: String,
    @SerializedName( "ItemCode")
    val itemCode: String,

    @SerializedName( "PriceList")
    val priceList: String?,

    @SerializedName( "Listname")
    val listName: String,

    @SerializedName( "Price")
    val price: String?,

    @SerializedName( "UM")
    val um: String,

    @SerializedName( "BaseQty")
    val baseQty: Int

)
