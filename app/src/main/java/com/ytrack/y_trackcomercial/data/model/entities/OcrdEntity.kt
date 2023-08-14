package com.ytrack.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OCRD")
data class OCRDEntity(
    @PrimaryKey
    val id: String,
    val Address: String,
    val CardCode: String,
    val CardName: String?
)