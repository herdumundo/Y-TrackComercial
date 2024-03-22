package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.ColumnInfo
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
     class Migration8to9 : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Crear la tabla OINV_POS
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `OINV_POS` (
                `docEntry`  BIGINT PRIMARY KEY NOT NULL,
                `cardCode` TEXT,
                `cardName` TEXT,
                `docDate` TEXT,
                `docDueDate` TEXT,
                `series` TEXT,
                `folioNumber` INTEGER,
                `numAtCard` TEXT,
                `slpCode` INTEGER,
                `timb` TEXT,
                `cdc` TEXT,
                `qr` TEXT,
                 `xmlFact` TEXT,
                `xmlNombre` TEXT,
                `estado` TEXT
            )
        """)

            // Crear la tabla INV1_POS
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `INV1_POS` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `docEntry` BIGINT NOT NULL,
                `LineNum` INTEGER NOT NULL,
                `ItemCode` TEXT NOT NULL,
                `ItemName` TEXT,
                `WhsCode` TEXT NOT NULL,
                `Quantity` INTEGER NOT NULL,
                `PriceAfterVat` INTEGER NOT NULL,
                `UoMEntry` INTEGER NOT NULL,
                `TaxCode` TEXT,
                FOREIGN KEY(`docEntry`) REFERENCES `OINV_POS`(`docEntry`) ON DELETE CASCADE
            )
        """)
        }
    }
/*
 qr=Column{name='qr', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 estado=Column{name='estado', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 docEntry=Column{name='docEntry', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='undefined'},
 cdc=Column{name='cdc', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 cardName=Column{name='cardName', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 docDueDate=Column{name='docDueDate', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 slpCode=Column{name='slpCode', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 cardCode=Column{name='cardCode', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 docDate=Column{name='docDate', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 numAtCard=Column{name='numAtCard', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 folioNumber=Column{name='folioNumber', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 series=Column{name='series', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 timb=Column{name='timb', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 xmlFact=Column{name='xmlFact', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 xmlNombre=Column{name='xmlNombre', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'}}, foreignKeys=[], indices=[]}
                                                                                                     Found:
 docEntry=Column{name='docEntry', type='INTEGER', affinity='3', notNull=true, primaryKeyPosition=1, defaultValue='undefined'},
 cardCode=Column{name='cardCode', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 cardName=Column{name='cardName', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 docDate=Column{name='docDate', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 docDueDate=Column{name='docDueDate', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 series=Column{name='series', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 folioNumber=Column{name='folioNumber', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 numAtCard=Column{name='numAtCard', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 slpCode=Column{name='slpCode', type='INTEGER', affinity='3', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 timb=Column{name='timb', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 cdc=Column{name='cdc', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 qr=Column{name='qr', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
  xmlFact=Column{name='xmlFact', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 xmlNombre=Column{name='xmlNombre', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'},
 estado=Column{name='estado', type='TEXT', affinity='2', notNull=false, primaryKeyPosition=0, defaultValue='undefined'}}, foreignKeys=[], indices=[]}

*
*
* */