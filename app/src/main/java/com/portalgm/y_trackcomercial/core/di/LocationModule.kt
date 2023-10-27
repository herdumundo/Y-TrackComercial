package com.portalgm.y_trackcomercial.core.di

import android.content.Context
import com.portalgm.y_trackcomercial.services.gps.servicioGMS.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationService(context: Context): LocationService {
        return LocationService(context)
    }
}
