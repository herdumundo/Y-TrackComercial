package com.example.y_trackcomercial.core.di

import android.content.Context
import androidx.room.Room
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.dao.HorariosUsuarioDao
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.dao.OcrdOitmDao
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.model.dao.OitmDao
import com.example.y_trackcomercial.model.dao.PermisosVisitasDao
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.dao.UbicacionesPvDao
import com.example.y_trackcomercial.model.dao.registroDaos.MovimientosDao
import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.LogDao
import com.example.y_trackcomercial.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.example.y_trackcomercial.model.entities.database.YtrackDatabase
 import com.example.y_trackcomercial.model.entities.database.migrations.Migration1to2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule() {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): YtrackDatabase {
          val migration1To2 = Migration1to2()
             return Room.databaseBuilder(appContext, YtrackDatabase::class.java, "YtrackDatabase")
            .addMigrations(migration1To2)
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
    fun provideVisitasDao(database: YtrackDatabase): VisitasDao {
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
    fun provideLogDao(database: YtrackDatabase): LogDao {
        return database.LogDao()
    }
    @Provides
    @Singleton
    fun provideAuditTrailDao(database: YtrackDatabase): AuditTrailDao {
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
    fun provideUbicacionesPvDao(database: YtrackDatabase): UbicacionesPvDao {
        return database.UbicacionesPvDao()
    }

    @Provides
    @Singleton
    fun provideMovimientosDao(database: YtrackDatabase): MovimientosDao {
        return database.MovimientosDao()
    }


}