package com.portalgm.y_trackcomercial.data.model.entities.database.migrations

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.annotations.SerializedName

class Migration8to9 : Migration(8, 9) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Crear la tabla OINV_POS
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `OINV_POS` (
                `docEntry`  BIGINT PRIMARY KEY NOT NULL,
                `idVisita`  BIGINT NOT NULL,
                `docEntryPedido` TEXT,
                `condicion` TEXT,
                
                `licTradNum` TEXT,
                `lineNumbCardCode` INTEGER NOT NULL,
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
                `iva` TEXT,
                `vigenciaTimbrado` TEXT,
                `tipoContribuyente` TEXT,
                `ci` TEXT,
                `address` TEXT,
                `correo` TEXT,
                `contado` TEXT,
                `totalIvaIncluido` TEXT,
                `anulado`        TEXT NOT NULL DEFAULT 'N',
                `pymntGroup` TEXT,
                `estado` TEXT,
                `docEntrySap` TEXT
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
                `PrecioUnitSinIva` TEXT,
                `PrecioUnitIvaInclu` TEXT,
                `TotalSinIva` TEXT,
                `TotalIva` TEXT,
                FOREIGN KEY(`docEntry`) REFERENCES `OINV_POS`(`docEntry`) ON DELETE CASCADE
            )
        """)
            // Crear la tabla INV1_LOTES_POS
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `INV1_LOTES_POS` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `docEntry` BIGINT NOT NULL,
                `ItemCode` TEXT NOT NULL,
                `Lote` TEXT,
                `LoteLargo` TEXT,
                `Quantity` INTEGER NOT NULL,
                `QuantityCalculado` TEXT NOT NULL,
                FOREIGN KEY(`docEntry`) REFERENCES `OINV_POS`(`docEntry`) ON DELETE CASCADE
            )
        """)
            // Crear la tabla A0_YTV_VENDEDOR
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `A0_YTV_VENDEDOR` (
            `slpcode` INTEGER NOT NULL PRIMARY KEY,
            `slpname` TEXT NOT NULL,
            `U_DEPOSITO` TEXT,
            `IdUsuario` TEXT NOT NULL,
            `U_SERIEFACT` INTEGER,
            `seriesname` TEXT NOT NULL,
            `Remark` TEXT,
            `u_ci` TEXT,
            `u_ayudante` TEXT,
            `nombre_ayudante` TEXT,
            `u_esta` TEXT NOT NULL,
            `u_pemi` TEXT NOT NULL,
            `ult_nro_fact` TEXT NOT NULL,
            `U_nro_autorizacion` TEXT,
            `U_TimbradoNro` TEXT,
            `U_fecha_autoriz_timb` TEXT,  
            `U_FechaVto` TEXT,
            `estado` TEXT NOT NULL DEFAULT 'C'
             )
        """)



            // Crear la tabla A0_YTV_VENDEDOR
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `A0_YTV_LISTA_PRECIOS` (
            `id` TEXT NOT NULL PRIMARY KEY,
            `ItemCode` TEXT NOT NULL,
            `PriceList` TEXT,
            `Listname` TEXT NOT NULL,
            `Price` TEXT
            `    
             )
        """)


            // Crear la tabla A0_YTV_STOCK_ALMACEN
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `A0_YTV_STOCK_ALMACEN` (
            `id` TEXT NOT NULL PRIMARY KEY,
            `ItemCode` TEXT ,
            `ItemName` TEXT,
            `WhsCode` TEXT,
            `Quantity` TEXT,
            `DistNumber` TEXT,
            `LoteLargo` TEXT,
            `UpdateDate` TEXT         
            `    
             )
        """)

            // Crear la tabla A0_YTV_ORDEN_VENTA
            database.execSQL("""
            CREATE TABLE IF NOT EXISTS `A0_YTV_ORDEN_VENTA` (
            `id`            TEXT    NOT NULL PRIMARY KEY,
            `LineNumDet`    INT     NOT NULL            ,
            `DocEntry`      TEXT    NOT NULL            ,
            `DocNum`        TEXT                        ,
            `DocDate`       TEXT    NOT NULL            ,
            `DocDueDate`    TEXT                        ,
            `DocTotal`      TEXT    NOT NULL            ,
            `CardCode`      TEXT                        ,
            `IdCliente`     TEXT                        ,
            `CardName`      TEXT                        ,
            `ShipToCode`    TEXT                        ,
            `LineNum`       TEXT    NOT NULL            ,
            `LicTradNum`    TEXT    NOT NULL            ,
            `ItemCode`      TEXT    NOT NULL            ,
            `ItemName`      TEXT    NOT NULL            ,
            `Quantity`      TEXT    NOT NULL            ,
            `unitMsr`       TEXT                        ,
            `PriceAfVAT`    TEXT                        ,
            `TaxCode`       TEXT                        ,
            `LineTotal`     TEXT                        ,
            `VatSum`        TEXT                        ,
            `SlpCode`       TEXT                        ,
            `SlpName`       TEXT                        ,
            `GroupNum`      TEXT                        ,
            `PymntGroup`    TEXT                        ,
            `DocStatus`     TEXT                        ,
            `CANCELED`      TEXT                        ,
            `estado`        TEXT NOT NULL DEFAULT 'P'
             `    
             )
        """)
            database.execSQL("ALTER TABLE OCRD ADD COLUMN CreditDisp TEXT")
            database.execSQL("ALTER TABLE OCRD ADD COLUMN CreditLine TEXT")
            database.execSQL("ALTER TABLE OCRD ADD COLUMN Balance TEXT")
        }
     }
