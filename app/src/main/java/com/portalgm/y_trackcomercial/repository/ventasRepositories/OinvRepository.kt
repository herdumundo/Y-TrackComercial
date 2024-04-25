package com.portalgm.y_trackcomercial.repository.ventasRepositories

import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgm.y_trackcomercial.data.model.entities.database.YtrackDatabase
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import javax.inject.Inject

class OinvRepository @Inject constructor
    (
    private val oinvPosDao: OINV_DAO,
    private val inv1DAO: INV1_DAO,
    private val inv1LotesDAO: INV1_LOTES_DAO,
    private val database: YtrackDatabase, // Inyecta la instancia de la base de datos

) {
    suspend fun getAllOinvPosWithDetails(docEntry:Long) = oinvPosDao.getOinvPosWithDetails(docEntry)

    suspend fun insertOinvPos(oinv: OINV_POS): Long {
        return oinvPosDao.insertOinv(oinv)
    }

    suspend fun updateFirmaOinvPos(qr: String,xml:String,cdc:String,docEntry: Long)  {
          oinvPosDao.updateFirmaFactura(qr,xml,cdc,docEntry)
    }


    suspend fun insertarVentaCompleta(
        oinv: OINV_POS,
        inv1List: List<INV1_POS>,
        inv1LotesList: List<INV1_LOTES_POS>,
        idOrdenVenta:String
    ):Long {
        // La lógica de transacción iría aquí.
      return  database.insertarVentaCompleta(oinv, inv1List, inv1LotesList, idOrdenVenta)
    }

    suspend fun getAllOinvPosByDate(fecha:String) = oinvPosDao.getOinvPosByDate(fecha)


}