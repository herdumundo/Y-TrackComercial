package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

     class Migration5to6 : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS nuevas_ubicaciones ( " +
                    "id BIGINT PRIMARY KEY NOT NULL , " +
                    "idOcrd TEXT NOT NULL , " +
                    "idUsuario INTEGER  NOT NULL , " +
                    "createdAt TEXT   NOT NULL , " +
                    "latitudPV REAL    NOT NULL , " +
                    "longitudPV REAL    NOT NULL , " +
                    "estado TEXT   NOT NULL )")
        }
    }
