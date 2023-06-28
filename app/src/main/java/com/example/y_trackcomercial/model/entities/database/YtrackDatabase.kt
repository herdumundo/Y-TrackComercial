package com.example.y_trackcomercial.model.entities.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.model.dao.PermisosVisitasDao
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.model.entities.LotesListasEntity
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity
import com.example.y_trackcomercial.model.entities.PermisosVisitasEntity
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity

@Database(
    entities =
    [
        OCRDEntity::class,
        LotesListasEntity::class,
        OcrdUbicacionEntity::class,
        VisitasEntity::class,
        RutasAccesosEntity::class,
        PermisosVisitasEntity::class
    ],
    version =1,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {

    // abstract fun usuariosDao(): UsuarioDao

    abstract fun customerDao(): CustomerDao

    abstract fun lotesListasDao(): LotesListasDao

    abstract fun ocrdUbicacionesDao(): OcrdUbicacionesDao


    abstract fun rutasAccesosDao(): RutasAccesosDao

    abstract fun VisitasDao(): VisitasDao

    abstract fun PermisosVisitasDao(): PermisosVisitasDao




}