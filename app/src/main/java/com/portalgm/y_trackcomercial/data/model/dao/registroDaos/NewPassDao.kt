package com.portalgm.y_trackcomercial.data.model.dao.registroDaos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portalgm.y_trackcomercial.data.api.request.NuevoPass
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity

@Dao
interface NewPassDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPass(newPassEntity: NewPassEntity): Long

    @Query("SELECT COUNT(*) FROM newpass where estado='P'")
    suspend fun getCountPendientes(): Int

    @Query("delete  FROM newpass ")
    fun eliminarTodos()


    @Query("select idUsuario,newPass as clave from newpass ")
    suspend fun getAllNuevoPass(): List<NuevoPass>

    @Query("UPDATE newpass SET estado='C' where estado='P'")
    suspend fun updateExportadoCerrado()

}