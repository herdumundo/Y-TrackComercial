package com.portalgm.y_trackcomercial.data.model.models

import androidx.room.Embedded
import androidx.room.Relation
import com.portalgm.y_trackcomercial.data.api.request.lotesDemovimientos
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.ventas.DetalleCompleto

data class OinvPosWithDetails(
    @Embedded val oinvPos: OINV_POS,
    @Relation(
        parentColumn = "docEntry",
        entityColumn = "docEntry"
    )
    val details: List<INV1_POS>,

    @Relation(
        parentColumn = "docEntry",
        entityColumn = "docEntry"
    )
    val detailsLotes: List<INV1_LOTES_POS>
)

data class LotesMovimientosOinv(
    val lotesDemovimientos: List<OinvPosWithDetails>
)

data class LotesMovimientosOinvNew(
    val lotesDemovimientos:List<DetalleCompleto>
)
