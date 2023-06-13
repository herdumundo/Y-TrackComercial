package com.example.y_trackcomercial.core.di

import android.content.Context
import androidx.room.Room
import com.example.y_trackcomercial.model.dao.CustomerDao
import com.example.y_trackcomercial.model.dao.LotesListasDao
import com.example.y_trackcomercial.model.dao.OcrdUbicacionesDao
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.entities.database.YtrackDatabase
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
        return Room.databaseBuilder(appContext, YtrackDatabase::class.java, "YtrackDatabase")
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

}