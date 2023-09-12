package com.portalgm.y_trackcomercial.data.model.entities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.portalgm.y_trackcomercial.data.model.dao.*
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.NewPassDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.UbicacionesNuevasDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.logsDaos.*
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.VisitasDao
import com.portalgm.y_trackcomercial.data.model.entities.*
import com.portalgm.y_trackcomercial.data.model.entities.logs.*
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity

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
        UbicacionesNuevasEntity::class,
        NewPassEntity::class,


     ],

    version =7,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {

    abstract fun customerDao():  CustomerDao

    abstract fun lotesListasDao():  LotesListasDao

    abstract fun ocrdUbicacionesDao(): OcrdUbicacionesDao

    abstract fun rutasAccesosDao(): RutasAccesosDao

    abstract fun VisitasDao():  VisitasDao

    abstract fun PermisosVisitasDao():  PermisosVisitasDao

    abstract fun HorariosUsuarioDao(): HorariosUsuarioDao

    abstract fun LogDao():  LogDao
    abstract fun AuditTrailDao(): AuditTrailDao

    abstract fun ParametrosDao():  ParametrosDao

    abstract fun OitmDao():  OitmDao

    abstract fun OcrdOitmDao():  OcrdOitmDao

    abstract fun MovimientosDao(): MovimientosDao
    abstract fun UbicacionesPvDao():  UbicacionesPvDao
    abstract fun UbicacionesNuevasDao():  UbicacionesNuevasDao
    abstract fun NewPassDao(): NewPassDao




}