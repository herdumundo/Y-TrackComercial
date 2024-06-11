package com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.dataviews

import androidx.room.DatabaseView

@DatabaseView("""
    SELECT 
        T0.ItemCode,
        T0.itemName,
        T0.WhsCode,
        T0.Quantity - IFNULL(SUM(CASE WHEN T2.docEntrySap = '-' and T2.anulado='N' THEN T1.Quantity ELSE 0 END), 0) AS quantiy,
        T0.distNumber,
        T0.loteLargo,
        T0.Quantity AS stockInicial4,
        T0.CodeBars
    FROM 
        A0_YTV_STOCK_ALMACEN T0
    LEFT JOIN 
        inv1_lotes_pos T1 ON T0.loteLargo = T1.LoteLargo
    LEFT JOIN 
        OINV_POS T2 ON T1.docEntry = T2.docEntry
    GROUP BY 
        T0.itemName, T0.ItemCode, T0.distNumber, T0.loteLargo, T0.WhsCode, T0.Quantity
""")
data class V_STOCK_ALMACEN(
    val itemCode: String?,
    val itemName: String?,
    val CodeBars: String?,
    val whsCode: String?,
    val quantiy: Long?,
    val distNumber: String?,
    val loteLargo: String?,
    val stockInicial: Long?,
)