package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2to3 : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ActivityLog ADD COLUMN bateria INTEGER NOT NULL DEFAULT 0")
    }
}
