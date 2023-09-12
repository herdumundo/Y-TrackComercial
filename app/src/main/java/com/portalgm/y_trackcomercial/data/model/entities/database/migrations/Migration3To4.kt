package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3to4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE ActivityLog ADD COLUMN estado TEXT NOT NULL DEFAULT 'P'")
    }
}
