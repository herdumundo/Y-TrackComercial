package com.example.y_trackcomercial.core.di

 import com.example.y_trackcomercial.services.gps.locationGMS.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
object LocationModule {
 // Método de proveedor para LocationService
 // Indicamos que se debe proporcionar una única instancia (Singleton)
 @Provides
 @Singleton
 fun provideLocationService(): LocationService {
  return LocationService()
 }
}
