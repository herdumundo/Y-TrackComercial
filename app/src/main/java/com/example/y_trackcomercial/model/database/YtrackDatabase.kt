package com.example.y_trackcomercial.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.y_trackcomercial.model.dao.UsuariosDao
import com.example.y_trackcomercial.model.entities.UsuariosEntity

@Database(
    entities = [UsuariosEntity::class],
    version = 1
)
abstract class YtrackDatabase : RoomDatabase() {

    abstract fun usuariosDao(): UsuariosDao


}