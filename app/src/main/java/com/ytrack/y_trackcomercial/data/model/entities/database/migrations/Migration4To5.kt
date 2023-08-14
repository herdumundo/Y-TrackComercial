package com.ytrack.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4to5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE MOVIMIENTOS ADD COLUMN estado TEXT NOT NULL DEFAULT 'P'")
    }
}
