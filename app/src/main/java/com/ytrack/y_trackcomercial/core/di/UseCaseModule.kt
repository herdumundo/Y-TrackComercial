package com.ytrack.y_trackcomercial.core.di

import com.ytrack.y_trackcomercial.repository.PermisosVisitasRepository
import com.ytrack.y_trackcomercial.repository.UbicacionesPvRepository
import com.ytrack.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.ytrack.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.ytrack.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.ytrack.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.ytrack.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.ytrack.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.ytrack.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.ytrack.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.ytrack.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.informeInventario.GetInformeUseCase
import com.ytrack.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.ytrack.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.ytrack.y_trackcomercial.usecases.marcacionPromotora.GetIdVisitaActivaUseCase
import com.ytrack.y_trackcomercial.usecases.marcacionPromotora.VerificarCierrePendienteUseCase
import com.ytrack.y_trackcomercial.usecases.marcacionPromotora.VerificarInventarioCierreVisitaUseCase
import com.ytrack.y_trackcomercial.usecases.permisoVisita.CountRegistrosPermisosVisitaUseCase
import com.ytrack.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.ytrack.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvCountUseCase
import com.ytrack.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvUseCase
import com.ytrack.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
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
    ): GetUbicacionesPvUseCase {
        return GetUbicacionesPvUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetIdVisitaActivaUseCase(
        visitasRepository: VisitasRepository
    ): GetIdVisitaActivaUseCase {
        return GetIdVisitaActivaUseCase(visitasRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideVerificarInventarioCierreVisitaUseCase(
        permisosVisitasRepository: PermisosVisitasRepository
    ): VerificarInventarioCierreVisitaUseCase {
        return VerificarInventarioCierreVisitaUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideVerificarCierrePendienteUseCaseUseCase(
        permisosVisitasRepository: PermisosVisitasRepository
    ): VerificarCierrePendienteUseCase {
        return VerificarCierrePendienteUseCase(permisosVisitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun providegetInformeUseCase(
        movimientosRepository: MovimientosRepository
    ): GetInformeUseCase {
        return GetInformeUseCase(movimientosRepository)
    }

    @Provides
    @ViewModelScoped
    fun providegetgetVisitasPendientesUseCase(
        visitasRepository: VisitasRepository
    ): GetVisitasPendientesUseCase {
        return GetVisitasPendientesUseCase(visitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun providegetEnviarVisitasPendientesUseCase(
        visitasRepository: VisitasRepository
    ): EnviarVisitasPendientesUseCase {
        return EnviarVisitasPendientesUseCase(visitasRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideCountCantidadPendientesUseCase(
        visitasRepository: VisitasRepository
    ): CountCantidadPendientes {
        return CountCantidadPendientes(visitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAuditTrailPendienteUseCase(
        auditTrailRepository: AuditTrailRepository
    ): GetAuditTrailPendienteUseCase {
        return GetAuditTrailPendienteUseCase(auditTrailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountAuditTrailUseCase(
        auditTrailRepository: AuditTrailRepository
    ): CountAuditTrailUseCase {
        return CountAuditTrailUseCase(auditTrailRepository)
    }
    @Provides
    @ViewModelScoped
    fun provideEnviarAuditTrailPendientesUseCase(
        auditTrailRepository: AuditTrailRepository
    ): EnviarAuditTrailPendientesUseCase {
        return EnviarAuditTrailPendientesUseCase(auditTrailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountLogPendientesUseCase(
        logRepository: LogRepository
    ): CountLogPendientesUseCase {
        return CountLogPendientesUseCase(logRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetLogPendienteUseCase(
        logRepository: LogRepository
    ): GetLogPendienteUseCase {
        return GetLogPendienteUseCase(logRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEnviarLogPendientesUseCase(
        logRepository: LogRepository
    ): EnviarLogPendientesUseCase {
        return EnviarLogPendientesUseCase(logRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountMovimientoUseCase(
        movimientosRepository: MovimientosRepository
    ): CountMovimientoUseCase {
        return CountMovimientoUseCase(movimientosRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideEnviarMovimientoPendientesUseCase(
        movimientosRepository: MovimientosRepository
    ): EnviarMovimientoPendientesUseCase {
        return EnviarMovimientoPendientesUseCase(movimientosRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovimientoPendientesUseCase(
        movimientosRepository: MovimientosRepository
    ): GetMovimientoPendientesUseCase {
        return GetMovimientoPendientesUseCase(movimientosRepository)
    }
/*
    @Provides
    @Singleton
    fun provideLocationManager(
        auditTrailRepository: AuditTrailRepository,
        sharedPreferences: SharedPreferences,
        logRepository: LogRepository,
        context: Context
    ): LocationManager {
        return LocationManager(auditTrailRepository, sharedPreferences, logRepository, context)
    }
 */
}

