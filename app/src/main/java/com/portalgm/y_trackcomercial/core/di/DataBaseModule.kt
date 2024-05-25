package com.portalgm.y_trackcomercial.core.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.Keep
import androidx.room.Room
import com.portalgm.y_trackcomercial.data.model.entities.database.migrations.*
import com.portalgm.y_trackcomercial.data.model.entities.database.*
import com.portalgm.y_trackcomercial.data.model.dao.*
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.NewPassDao
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.UbicacionesNuevasDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.VisitasDao
import com.portalgm.y_trackcomercial.data.model.dao.registroDaos.logsDaos.*
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_LISTA_PRECIOSDAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_ORDEN_VENTADAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_STOCK_ALMACENDAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_VENDEDORDAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgm.y_trackcomercial.data.model.dao.ventasDaos.views.daos.StockDao


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Keep
@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule  {

    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context):  YtrackDatabase {
        val migration1To2 =
            Migration1to2()
        val migration2To3 =
            Migration2to3()
        val migration3to4 =
            Migration3to4()
        val migration4to5 =
            Migration4to5()
        val migration5to6 =
            Migration5to6()
        val migration6to7 =
            Migration6to7()
        val migration7to8 =
            Migration7to8()
        val migration8to9 =
            Migration8to9()

        return Room.databaseBuilder(appContext, YtrackDatabase::class.java, "YtrackDatabase")
            .addMigrations(migration8to9)
            .build()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(database: YtrackDatabase): CustomerDao {
        return database.customerDao()
         }

    @Provides
    @Singleton
    fun provideLotesListasDao(database: YtrackDatabase): LotesListasDao {
        return database.lotesListasDao()
    }

    @Provides
    @Singleton
    fun provideOcrdUbicacionesDao(database: YtrackDatabase): OcrdUbicacionesDao {
        return database.ocrdUbicacionesDao()
    }

    @Provides
    @Singleton
    fun provideRutasAccesosDao(database: YtrackDatabase): RutasAccesosDao {
        return database.rutasAccesosDao()
    }

    @Provides
    @Singleton
    fun provideVisitasDao(database: YtrackDatabase):  VisitasDao {
        return database.VisitasDao()
    }

    @Provides
    @Singleton
    fun provideVisitasPermisosDao(database: YtrackDatabase): PermisosVisitasDao {
        return database.PermisosVisitasDao()
    }

    @Provides
    @Singleton
    fun provideHorariosUsuarioDao(database: YtrackDatabase): HorariosUsuarioDao {
        return database.HorariosUsuarioDao()
    }

    @Provides
    @Singleton
    fun provideLogDao(database: YtrackDatabase):  LogDao {
        return database.LogDao()
    }
    @Provides
    @Singleton
    fun provideAuditTrailDao(database: YtrackDatabase):  AuditTrailDao {
        return database.AuditTrailDao()
    }

    @Provides
    @Singleton
    fun provideOitmDao(database: YtrackDatabase): OitmDao {
        return database.OitmDao()
    }


    @Provides
    @Singleton
    fun provideOcrdOitmDao(database: YtrackDatabase): OcrdOitmDao {
        return database.OcrdOitmDao()
    }

    @Provides
    @Singleton
    fun provideUbicacionesPvDao(database:  YtrackDatabase): UbicacionesPvDao {
        return database.UbicacionesPvDao()
    }

    @Provides
    @Singleton
    fun provideMovimientosDao(database: YtrackDatabase):  MovimientosDao {
        return database.MovimientosDao()
    }


    @Provides
    @Singleton
    fun provideUbicacionNuevaDao(database: YtrackDatabase): UbicacionesNuevasDao {
        return database.UbicacionesNuevasDao()
    }


    @Provides
    @Singleton
    fun provideNewPassDao(database: YtrackDatabase): NewPassDao {
        return database.NewPassDao()
    }

    @Provides
    @Singleton
    fun provideParametrosDao(database: YtrackDatabase): ParametrosDao {
        return database.ParametrosDao()
    }

    @Provides
    @Singleton
    fun provideOinvDao(database: YtrackDatabase): OINV_DAO {
        return database.OinvDao()
    }

    @Provides
    @Singleton
    fun provideA0_YTV_VENDEDORDAO(database: YtrackDatabase): A0_YTV_VENDEDORDAO {
        return database.A0_YTV_VENDEDOR_DAO()
    }

    @Provides
    @Singleton
    fun provideA0_YTV_LISTA_PRECIOSDAO(database: YtrackDatabase): A0_YTV_LISTA_PRECIOSDAO {
        return database.A0_YTV_LISTA_PRECIOS_DAO()
    }
    @Provides
    @Singleton
    fun provideAlmacenStockDAO(database: YtrackDatabase): A0_YTV_STOCK_ALMACENDAO {
        return database.A0_YTV_STOCK_ALMACEN_DAO()
    }

    @Provides
    @Singleton
    fun provideOrdenVentaDAO(database: YtrackDatabase): A0_YTV_ORDEN_VENTADAO {
        return database.A0_YTV_ORDEN_VENTADAO_DAO()
    }

    @Provides
    @Singleton
    fun provideINV1_DAO(database: YtrackDatabase): INV1_DAO {
        return database.INV1_DAO()
    }

    @Provides
    @Singleton
    fun provideINV1_LOTES_DAO(database: YtrackDatabase): INV1_LOTES_DAO {
        return database.INV1_LOTES_DAO()
    }
    @Provides
    @Singleton
    fun provideSTOCK_DAO(database: YtrackDatabase): StockDao {
        return database.stockDao()
    }


}