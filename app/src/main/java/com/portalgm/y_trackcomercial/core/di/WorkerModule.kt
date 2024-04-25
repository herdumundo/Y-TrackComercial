package com.portalgm.y_trackcomercial.core.di

import androidx.annotation.Keep
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import com.portalgm.y_trackcomercial.usecases.newPass.ExportarNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.GetNewPassPendientesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ForWorker

@Keep
@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {
    @Provides
    @ForWorker
     fun provideExportarNewPassPendientesUseCaseSingleton(
        newPassRepository: NewPassRepository
    ): ExportarNewPassPendientesUseCase {
        return ExportarNewPassPendientesUseCase(newPassRepository)
    }

    @Provides
    @ForWorker
    fun provideGetNewPassPendientesUseCaseSingleton(
        newPassRepository: NewPassRepository
    ): GetNewPassPendientesUseCase {
        return GetNewPassPendientesUseCase(newPassRepository)
    }

}

