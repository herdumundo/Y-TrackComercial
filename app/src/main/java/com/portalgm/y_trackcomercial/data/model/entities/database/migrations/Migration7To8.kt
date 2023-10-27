package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

     class Migration7to8 : Migration(7, 8) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("  ALTER TABLE AuditTrail  ADD COLUMN idVisita BIGINT NOT NULL DEFAULT 0 ")
            database.execSQL("  ALTER TABLE AuditTrail  ADD distanciaPV   INTEGER NOT NULL DEFAULT 1000000");
            database.execSQL("  ALTER TABLE AuditTrail  ADD tiempo   INTEGER NOT NULL DEFAULT 0");
            database.execSQL("  ALTER TABLE AuditTrail  ADD tipoRegistro   TEXT NOT NULL DEFAULT '' ");


        }
    }
