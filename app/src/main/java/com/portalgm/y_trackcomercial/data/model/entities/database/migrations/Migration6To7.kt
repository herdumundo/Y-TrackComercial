package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

     class Migration6to7 : Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS NewPass ( " +
                    "id BIGINT PRIMARY KEY NOT NULL ," +
                    "idUsuario INTEGER  NOT NULL , " +
                    "newPass TEXT   NOT NULL , " +
                    "estado TEXT   NOT NULL )")
        }
    }
