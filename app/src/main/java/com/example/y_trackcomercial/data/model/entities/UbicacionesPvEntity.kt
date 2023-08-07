package com.example.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable


@Entity(tableName = "UBICACIONES_PV")
data class UbicacionesPvEntity(
    @PrimaryKey
    var id: Int,
    @Nullable
    var descripcion: String?
)