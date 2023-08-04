package com.example.y_trackcomercial.model.entities.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.dao.HorariosUsuarioDao
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.dao.OcrdOitmDao
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.model.dao.OitmDao
import com.example.y_trackcomercial.model.dao.ParametrosDao
import com.example.y_trackcomercial.model.dao.PermisosVisitasDao
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.dao.UbicacionesPvDao
import com.example.y_trackcomercial.model.dao.registroDaos.MovimientosDao
import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.LogDao
import com.example.y_trackcomercial.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.example.y_trackcomercial.model.entities.LotesListasEntity
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.model.entities.OcrdUbicacionEntity
import com.example.y_trackcomercial.model.entities.PermisosVisitasEntity
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity
import com.example.y_trackcomercial.model.entities.HorariosUsuarioEntity
import com.example.y_trackcomercial.model.entities.OcrdOitmEntity
import com.example.y_trackcomercial.model.entities.OitmEntity
import com.example.y_trackcomercial.model.entities.ParametrosEntity
import com.example.y_trackcomercial.model.entities.UbicacionesPvEntity
import com.example.y_trackcomercial.model.entities.logs.AuditTrailEntity
import com.example.y_trackcomercial.model.entities.logs.LogEntity
import com.example.y_trackcomercial.model.entities.registro_entities.MovimientosEntity
import com.example.y_trackcomercial.model.entities.registro_entities.VisitasEntity

@Database(
    entities =
    [
        OCRDEntity::class,
        LotesListasEntity::class,
        OcrdUbicacionEntity::class,
        VisitasEntity::class,
        RutasAccesosEntity::class,
        HorariosUsuarioEntity::class,
        LogEntity::class,
        AuditTrailEntity::class,
        ParametrosEntity::class,
        OitmEntity::class,
        OcrdOitmEntity::class,
        MovimientosEntity::class,
        UbicacionesPvEntity::class,
        PermisosVisitasEntity::class,
     ],

    version =5,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {


    abstract fun customerDao(): CustomerDao

    abstract fun lotesListasDao(): LotesListasDao

    abstract fun ocrdUbicacionesDao(): OcrdUbicacionesDao

    abstract fun rutasAccesosDao(): RutasAccesosDao

    abstract fun VisitasDao(): VisitasDao

    abstract fun PermisosVisitasDao(): PermisosVisitasDao

    abstract fun HorariosUsuarioDao(): HorariosUsuarioDao

    abstract fun LogDao(): LogDao
    abstract fun AuditTrailDao(): AuditTrailDao

    abstract fun ParametrosDao(): ParametrosDao

    abstract fun OitmDao(): OitmDao

    abstract fun OcrdOitmDao(): OcrdOitmDao

    abstract fun MovimientosDao(): MovimientosDao
    abstract fun UbicacionesPvDao(): UbicacionesPvDao



}