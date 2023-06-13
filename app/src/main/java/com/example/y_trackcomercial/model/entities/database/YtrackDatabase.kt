package com.example.y_trackcomercial.model.entities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.entities.LotesListasEntity
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity

@Database(
    entities =
    [
        OCRDEntity::class,
        LotesListasEntity::class,
        OcrdUbicacionEntity::class,
        RutasAccesosEntity::class
    ],
    version =3,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {

    // abstract fun usuariosDao(): UsuarioDao

    abstract fun customerDao(): CustomerDao

    abstract fun lotesListasDao(): LotesListasDao

    abstract fun ocrdUbicacionesDao(): OcrdUbicacionesDao


    abstract fun rutasAccesosDao(): RutasAccesosDao


}