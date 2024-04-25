package com.portalgm.y_trackcomercial.repository.ventasRepositories

import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.Lotes
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1Detalle
import javax.inject.Inject



class INV1_REPOSITORY @Inject constructor
    (
    private val INV1_DAO: INV1_DAO

) {

    suspend fun insertInv1Bulk(lotesList: List<Inv1Detalle>) {
        //  val createdAtLongVar=System.currentTimeMillis()
        val movimientosList = lotesList.map { lote ->
            INV1_POS(
              itemCode = lote.ItemCode,
              docEntry= lote.docEntry,
              lineNum= lote.LineNum,
              itemName= lote.ItemName,
              whsCode= lote.WhsCode,
              quantity= lote.Quantity.toString(),
              priceAfterVat= lote.PriceAfterVat,
              precioUnitSinIva= lote.PrecioUnitSinIva.toString(),
              precioUnitIvaInclu= lote.PrecioUnitIvaInclu,
              totalSinIva= lote.TotalSinIva,
              totalIva= lote.TotalIva,
              uomEntry =  lote.UoMEntry,
              taxCode =  lote.TaxCode
            )
        }

        INV1_DAO.insertAllInv1(movimientosList)
    }
}