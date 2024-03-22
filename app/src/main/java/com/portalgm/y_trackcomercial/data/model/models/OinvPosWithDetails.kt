package com.portalgm.y_trackcomercial.data.model.models

import androidx.room.Embedded
import androidx.room.Relation
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.OINV_POS

data class OinvPosWithDetails(
    @Embedded val oinvPos: OINV_POS,
    @Relation(
        parentColumn = "docEntry",
        entityColumn = "docEntry"
    )
    val details: List<INV1_POS>
)