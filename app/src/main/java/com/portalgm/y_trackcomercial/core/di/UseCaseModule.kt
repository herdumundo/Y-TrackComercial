package com.portalgm.y_trackcomercial.core.di

import androidx.annotation.Keep
import com.portalgm.y_trackcomercial.repository.ApiKeyGMSRepository
import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import com.portalgm.y_trackcomercial.repository.UbicacionesPvRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.usecases.apiKeyGMS.GetApiKeyGMSUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.informeInventario.GetInformeUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.marcacionPromotora.GetIdVisitaActivaUseCase
import com.portalgm.y_trackcomercial.usecases.marcacionPromotora.VerificarCierrePendienteUseCase
import com.portalgm.y_trackcomercial.usecases.marcacionPromotora.VerificarInventarioCierreVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.InsertarNewPassUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.InsertarNuevaUbicacionUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.CountRegistrosPermisosVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvCountUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.visitas.GetHorasTranscurridasVisitasUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Keep

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

    @Provides
    @ViewModelScoped
    fun provideGetHorasTranscurridasVisitasUseCase(
        visitasRepository: VisitasRepository
    ):  GetHorasTranscurridasVisitasUseCase {
        return GetHorasTranscurridasVisitasUseCase(visitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetApiKeyGMSUseCase(
        apiKeyGMSRepository: ApiKeyGMSRepository
    ):  GetApiKeyGMSUseCase {
        return GetApiKeyGMSUseCase(apiKeyGMSRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertarNuevaUbicacionUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ):  InsertarNuevaUbicacionUseCase {
        return InsertarNuevaUbicacionUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountUbicacionesNuevasPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ):  CountUbicacionesNuevasPendientesUseCase {
        return CountUbicacionesNuevasPendientesUseCase(nuevaUbicacionRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetNuevasUbicacionesPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ):  GetNuevasUbicacionesPendientesUseCase {
        return GetNuevasUbicacionesPendientesUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportarNuevasUbicacionesPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ):  ExportarNuevasUbicacionesPendientesUseCase {
        return ExportarNuevasUbicacionesPendientesUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertarNewPassUseCase(
        newPassRepository: NewPassRepository
    ):  InsertarNewPassUseCase {
        return InsertarNewPassUseCase(newPassRepository)
    }


}

