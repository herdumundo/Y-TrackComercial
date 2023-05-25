package com.example.y_trackcomercial.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.y_trackcomercial.model.entities.UsuariosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuariosDao {
    @Query("SELECT * from UsuariosEntity")

    fun getUsuarios(): Flow<List<UsuariosEntity>>

    @Insert
    suspend fun insertUsuarios(insertUsuarios: UsuariosEntity)

    @Update
    suspend fun updateUsuarios(updateUsuarios: UsuariosEntity)

    @Delete
    suspend fun deleteUsuarios
                (deleteUsuarios: UsuariosEntity)
}
