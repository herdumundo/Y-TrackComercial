package com.example.y_trackcomercial.core.di

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
import com.example.y_trackcomercial.data.model.dao.CustomerDao
import com.example.y_trackcomercial.data.model.dao.HorariosUsuarioDao
import com.example.y_trackcomercial.data.model.dao.LotesListasDao
import com.example.y_trackcomercial.data.model.dao.OcrdOitmDao
import com.example.y_trackcomercial.data.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.data.model.dao.OitmDao
import com.example.y_trackcomercial.data.model.dao.PermisosVisitasDao
import com.example.y_trackcomercial.data.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.data.model.dao.UbicacionesPvDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.VisitasDao
import com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao
import com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase
 import com.example.y_trackcomercial.data.model.entities.database.migrations.Migration1to2
import com.example.y_trackcomercial.data.model.entities.database.migrations.Migration2to3
import com.example.y_trackcomercial.data.model.entities.database.migrations.Migration3to4
import com.example.y_trackcomercial.data.model.entities.database.migrations.Migration4to5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule  {

    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase {
        val migration1To2 =
            com.example.y_trackcomercial.data.model.entities.database.migrations.Migration1to2()
        val migration2To3 =
            com.example.y_trackcomercial.data.model.entities.database.migrations.Migration2to3()
        val migration3to4 =
            com.example.y_trackcomercial.data.model.entities.database.migrations.Migration3to4()
        val migration4to5 =
            com.example.y_trackcomercial.data.model.entities.database.migrations.Migration4to5()

             return Room.databaseBuilder(appContext, com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase::class.java, "YtrackDatabase")
            .addMigrations(migration4to5)
            .build()
    }

    @Provides
    @Singleton
    fun provideCustomerDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.CustomerDao {
        return database.customerDao()
         }

    @Provides
    @Singleton
    fun provideLotesListasDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.LotesListasDao {
        return database.lotesListasDao()
    }

    @Provides
    @Singleton
    fun provideOcrdUbicacionesDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.OcrdUbicacionesDao {
        return database.ocrdUbicacionesDao()
    }

    @Provides
    @Singleton
    fun provideRutasAccesosDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.RutasAccesosDao {
        return database.rutasAccesosDao()
    }

    @Provides
    @Singleton
    fun provideVisitasDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.registroDaos.VisitasDao {
        return database.VisitasDao()
    }

    @Provides
    @Singleton
    fun provideVisitasPermisosDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.PermisosVisitasDao {
        return database.PermisosVisitasDao()
    }

    @Provides
    @Singleton
    fun provideHorariosUsuarioDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.HorariosUsuarioDao {
        return database.HorariosUsuarioDao()
    }

    @Provides
    @Singleton
    fun provideLogDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.LogDao {
        return database.LogDao()
    }
    @Provides
    @Singleton
    fun provideAuditTrailDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.registroDaos.logsDaos.AuditTrailDao {
        return database.AuditTrailDao()
    }

    @Provides
    @Singleton
    fun provideOitmDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.OitmDao {
        return database.OitmDao()
    }


    @Provides
    @Singleton
    fun provideOcrdOitmDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.OcrdOitmDao {
        return database.OcrdOitmDao()
    }

    @Provides
    @Singleton
    fun provideUbicacionesPvDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.UbicacionesPvDao {
        return database.UbicacionesPvDao()
    }

    @Provides
    @Singleton
    fun provideMovimientosDao(database: com.example.y_trackcomercial.data.model.entities.database.YtrackDatabase): com.example.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao {
        return database.MovimientosDao()
    }


}