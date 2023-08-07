package com.example.y_trackcomercial.core.di

import android.content.Context
import androidx.work.WorkerParameters
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.services.exportacion.ExportWorker
import com.example.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    fun provideExportWorker(
        context: Context,
        params: WorkerParameters,
        auditTrailRepository: AuditTrailRepository
    ): ExportWorker {
        return ExportWorker(context, params,auditTrailRepository )
    }
}

