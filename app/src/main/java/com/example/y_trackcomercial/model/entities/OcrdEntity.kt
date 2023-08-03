package com.example.y_trackcomercial.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity(tableName = "OCRD")
data class OCRDEntity(
    @PrimaryKey
    val id: String,
    val Address: String,
    val CardCode: String,
      val CardName: String?
)