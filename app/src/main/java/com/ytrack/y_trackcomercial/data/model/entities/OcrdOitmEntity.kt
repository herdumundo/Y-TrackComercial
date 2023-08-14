package com.ytrack.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable


@Entity(tableName = "OCRD_X_OITM")
data class OcrdOitmEntity(

    @PrimaryKey(autoGenerate = true)
    var codigo: Int,

    var id: String,
    @Nullable
    var CardCode: String?,
    @Nullable
    var LineNum: Int?,
    @Nullable
    var ItemCode: String?,
    @Nullable
    var ShipToCode: String?,
)