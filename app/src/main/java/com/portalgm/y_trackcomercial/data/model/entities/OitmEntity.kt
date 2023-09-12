package com.portalgm.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable


@Entity(tableName = "OITM")
data class OitmEntity(
    @PrimaryKey
    var ItemCode: String,
    @Nullable
    var ItemName: String?,
    @Nullable
    var CodeBars: String?,
    @Nullable
    var U_TIPOHUEVO: String?,
    @Nullable
    var Marca: String?,
    @Nullable
    var U_CanCaja:String?,
)