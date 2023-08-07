package com.example.y_trackcomercial.data.model.entities.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.y_trackcomercial.data.model.dao.CustomerDao
import com.example.y_trackcomercial.data.model.dao.HorariosUsuarioDao
import com.example.y_trackcomercial.data.model.dao.LotesListasDao
import com.example.y_trackcomercial.data.model.dao.OcrdOitmDao
import com.example.y_trackcomercial.data.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.data.model.dao.OitmDao
import com.example.y_trackcomercial.data.model.dao.ParametrosDao
import com.example.y_trackcomercial.data.model.dao.PermisosVisitasDao
import com.example.y_trackcomercial.data.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.data.model.dao.UbicacionesPvDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.example.y_trackcomercial.data.model.entities.LotesListasEntity
import com.example.y_trackcomercial.data.model.entities.OCRDEntity
import com.example.y_trackcomercial.data.model.entities.OcrdUbicacionEntity
import com.example.y_trackcomercial.data.model.entities.PermisosVisitasEntity
import com.example.y_trackcomercial.data.model.entities.RutasAccesosEntity
import com.example.y_trackcomercial.data.model.entities.HorariosUsuarioEntity
import com.example.y_trackcomercial.data.model.entities.OcrdOitmEntity
import com.example.y_trackcomercial.data.model.entities.OitmEntity
import com.example.y_trackcomercial.data.model.entities.ParametrosEntity
import com.example.y_trackcomercial.data.model.entities.UbicacionesPvEntity
import com.example.y_trackcomercial.data.model.entities.logs.AuditTrailEntity
import com.example.y_trackcomercial.data.model.entities.logs.LogEntity
import com.example.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity
import com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity

@Database(
    entities =
    [
        com.example.y_trackcomercial.data.model.entities.OCRDEntity::class,
        com.example.y_trackcomercial.data.model.entities.LotesListasEntity::class,
        com.example.y_trackcomercial.data.model.entities.OcrdUbicacionEntity::class,
        com.example.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity::class,
        com.example.y_trackcomercial.data.model.entities.RutasAccesosEntity::class,
        com.example.y_trackcomercial.data.model.entities.HorariosUsuarioEntity::class,
        com.example.y_trackcomercial.data.model.entities.logs.LogEntity::class,
        com.example.y_trackcomercial.data.model.entities.logs.AuditTrailEntity::class,
        com.example.y_trackcomercial.data.model.entities.ParametrosEntity::class,
        com.example.y_trackcomercial.data.model.entities.OitmEntity::class,
        com.example.y_trackcomercial.data.model.entities.OcrdOitmEntity::class,
        com.example.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity::class,
        com.example.y_trackcomercial.data.model.entities.UbicacionesPvEntity::class,
        com.example.y_trackcomercial.data.model.entities.PermisosVisitasEntity::class,
     ],

    version =5,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {


    abstract fun customerDao(): com.example.y_trackcomercial.data.model.dao.CustomerDao

    abstract fun lotesListasDao(): com.example.y_trackcomercial.data.model.dao.LotesListasDao

    abstract fun ocrdUbicacionesDao(): com.example.y_trackcomercial.data.model.dao.OcrdUbicacionesDao

    abstract fun rutasAccesosDao(): com.example.y_trackcomercial.data.model.dao.RutasAccesosDao

    abstract fun VisitasDao(): com.example.y_trackcomercial.data.model.dao.registroDaos.VisitasDao

    abstract fun PermisosVisitasDao(): com.example.y_trackcomercial.data.model.dao.PermisosVisitasDao

    abstract fun HorariosUsuarioDao(): com.example.y_trackcomercial.data.model.dao.HorariosUsuarioDao

    abstract fun LogDao(): com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao
    abstract fun AuditTrailDao(): com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao

    abstract fun ParametrosDao(): com.example.y_trackcomercial.data.model.dao.ParametrosDao

    abstract fun OitmDao(): com.example.y_trackcomercial.data.model.dao.OitmDao

    abstract fun OcrdOitmDao(): com.example.y_trackcomercial.data.model.dao.OcrdOitmDao

    abstract fun MovimientosDao(): com.example.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
    abstract fun UbicacionesPvDao(): com.example.y_trackcomercial.data.model.dao.UbicacionesPvDao



}