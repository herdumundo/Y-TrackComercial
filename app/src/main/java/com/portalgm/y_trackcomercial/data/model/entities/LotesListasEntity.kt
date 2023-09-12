package com.portalgm.y_trackcomercial.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LOTES_LISTA")
data class LotesListasEntity(
    @PrimaryKey
    
    val id: String,
    val FechaProd: String,
    val ItemCode: String,
    val ItemName: String,
    val CodeBars: String?,
    val FCP: String,
)