package com.example.y_trackcomercial.core.di

import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import com.example.y_trackcomercial.repository.UbicacionesPvRepository
import com.example.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.example.y_trackcomercial.usecases.marcacionPromotora.GetIdVisitaActivaUseCase
import com.example.y_trackcomercial.usecases.permisoVisita.CountRegistrosPermisosVisitaUseCase
import com.example.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.example.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvCountUseCase
import com.example.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvUseCase
import com.example.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFetchUbicacionesPvUseCase(
        ubicacionesPvRepository: UbicacionesPvRepository
    ): ImportarUbicacionesPvUseCase {
        return ImportarUbicacionesPvUseCase(ubicacionesPvRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUbicacionesPvCountUseCase(
        ubicacionesPvRepository: UbicacionesPvRepository
    ): GetUbicacionesPvCountUseCase {
        return GetUbicacionesPvCountUseCase(ubicacionesPvRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarPermisoVisitaUseCase(
        permisosVisitasRepository: PermisosVisitasRepository
    ): ImportarPermisoVisitaUseCase {
        return ImportarPermisoVisitaUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountRegistrosPermisosVisitaUseCase(
        permisosVisitasRepository: PermisosVisitasRepository
    ): CountRegistrosPermisosVisitaUseCase {
        return CountRegistrosPermisosVisitaUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUbicacionesPvUseCase(
        permisosVisitasRepository: UbicacionesPvRepository
    ): GetUbicacionesPvUseCase  {
        return GetUbicacionesPvUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetIdVisitaActivaUseCase(
        visitasRepository: VisitasRepository
    ): GetIdVisitaActivaUseCase  {
        return GetIdVisitaActivaUseCase(visitasRepository)
    }


}

