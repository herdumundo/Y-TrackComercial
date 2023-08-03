package com.example.y_trackcomercial.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.y_trackcomercial.model.entities.registro_entities.MovimientosEntity
import com.example.y_trackcomercial.model.models.InformeInventario

@Dao
interface MovimientosDao {
     @Transaction
    @Insert
    suspend fun insertAllMovimiento(Movimiento: List<MovimientosEntity>)

    @Query("select  t1.ocrdName,t2.id,t2.createdAt,t2.itemCode,t2.itemName,t2.codebar,t2.ubicacion,t2.lote,t2.cantidad,'Pendiente' as estado  from visitas t1 inner join movimientos t2  on t1.id=t2.idVisitas  where  strftime('%d/%m/%Y', t2.createdAt) =:fecha ")
    suspend fun getInformeMovimiento(fecha: String): MutableList<InformeInventario>


}