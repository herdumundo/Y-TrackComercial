package com.portalgm.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OCRD_UBICACION"/*, foreignKeys = [ForeignKey(entity = OCRDEntity::class, parentColumns = ["id"], childColumns = ["idCab"])]*/)

data class OcrdUbicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idCab: String,
    val latitud: String,
    val longitud: String
)
