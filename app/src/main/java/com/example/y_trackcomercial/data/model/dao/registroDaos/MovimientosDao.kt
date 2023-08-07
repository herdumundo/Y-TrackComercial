package com.example.y_trackcomercial.data.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.data.api.request.lotesDemovimientos

@Dao
interface MovimientosDao {
    @Transaction
    @Insert
    suspend fun insertAllMovimiento(Movimiento: List<com.example.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity>)

    @Query("select  t1.ocrdName,t2.id,t2.createdAt,t2.itemCode,t2.itemName,t2.codebar,t2.ubicacion,t2.lote,t2.cantidad,'Pendiente' as estado  from visitas t1 inner join movimientos t2  on t1.id=t2.idVisitas  where  strftime('%d/%m/%Y', t2.createdAt) =:fecha ")
    suspend fun getInformeMovimiento(fecha: String): MutableList<com.example.y_trackcomercial.data.model.models.InformeInventario>

    @Query("SELECT COUNT(*) FROM movimientos where estado='P'")
    suspend fun getCountPendientes(): Int


    @Query(
        "select " +
                "createdAtLong as id," +
                "idUsuario," +
                "userName, " +
                "idTipoMov," +
                "itemCode," +
                "codebar," +
                "strftime('%Y-%m-%d %H:%M:%S', createdAt)  as createdAt," +
                "createdAtLong,  " +
                "strftime('%Y-%m-%d %H:%M:%S', createdAt)  as updatedAt," +
                "ubicacion," +
                "itemName," +
                "cantidad," +
                "lote," +
                "idVisitas," +
                "loteLargo," +
                "loteCorto," +
                "obs from movimientos where estado='P'"
    )
    suspend fun getAllMovimientosExportar(): List<lotesDemovimientos>

    @Query("UPDATE movimientos SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado(  )

}