package com.ytrack.y_trackcomercial.data.model.entities.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =
    [
        com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.LotesListasEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.OcrdUbicacionEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.RutasAccesosEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.HorariosUsuarioEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.logs.LogEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.logs.AuditTrailEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.ParametrosEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.OitmEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.OcrdOitmEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.UbicacionesPvEntity::class,
        com.ytrack.y_trackcomercial.data.model.entities.PermisosVisitasEntity::class,
     ],

    version =5,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {


    abstract fun customerDao(): com.ytrack.y_trackcomercial.data.model.dao.CustomerDao

    abstract fun lotesListasDao(): com.ytrack.y_trackcomercial.data.model.dao.LotesListasDao

    abstract fun ocrdUbicacionesDao(): com.ytrack.y_trackcomercial.data.model.dao.OcrdUbicacionesDao

    abstract fun rutasAccesosDao(): com.ytrack.y_trackcomercial.data.model.dao.RutasAccesosDao

    abstract fun VisitasDao(): com.ytrack.y_trackcomercial.data.model.dao.registroDaos.VisitasDao

    abstract fun PermisosVisitasDao(): com.ytrack.y_trackcomercial.data.model.dao.PermisosVisitasDao

    abstract fun HorariosUsuarioDao(): com.ytrack.y_trackcomercial.data.model.dao.HorariosUsuarioDao

    abstract fun LogDao(): com.ytrack.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao
    abstract fun AuditTrailDao(): com.ytrack.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao

    abstract fun ParametrosDao(): com.ytrack.y_trackcomercial.data.model.dao.ParametrosDao

    abstract fun OitmDao(): com.ytrack.y_trackcomercial.data.model.dao.OitmDao

    abstract fun OcrdOitmDao(): com.ytrack.y_trackcomercial.data.model.dao.OcrdOitmDao

    abstract fun MovimientosDao(): com.ytrack.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
    abstract fun UbicacionesPvDao(): com.ytrack.y_trackcomercial.data.model.dao.UbicacionesPvDao



}