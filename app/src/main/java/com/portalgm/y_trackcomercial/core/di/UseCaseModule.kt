package com.portalgm.y_trackcomercial.core.di

import androidx.annotation.Keep
import com.portalgm.y_trackcomercial.repository.ApiKeyGMSRepository
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.ParametrosRepository
import com.portalgm.y_trackcomercial.repository.PermisosVisitasRepository
import com.portalgm.y_trackcomercial.repository.UbicacionesPvRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NewPassRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.OinvRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_LISTA_PRECIOSRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_ORDEN_VENTARepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YTV_STOCK_ALMACENRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.A0_YtvVentasRepository
import com.portalgm.y_trackcomercial.repository.ventasRepositories.INV1_LOTES_REPOSITORY
import com.portalgm.y_trackcomercial.repository.ventasRepositories.INV1_REPOSITORY
import com.portalgm.y_trackcomercial.usecases.apiKeyGMS.GetApiKeyGMSUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetUltimaHoraRegistroUseCase
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
import com.portalgm.y_trackcomercial.usecases.newPass.CountNewPassPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.ExportarNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.GetNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.InsertarNewPassUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.InsertarNuevaUbicacionUseCase
import com.portalgm.y_trackcomercial.usecases.ocrd.GetOcrdUseCase
import com.portalgm.y_trackcomercial.usecases.ocrd.ImportarOcrdUseCase
import com.portalgm.y_trackcomercial.usecases.ocrdUbicaciones.ImportarOcrdUbicacionesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvUseCase
import com.portalgm.y_trackcomercial.usecases.parametros.GetTimerGpsHilo1UseCase
import com.portalgm.y_trackcomercial.usecases.parametros.ImportarParametrosUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.CountRegistrosPermisosVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.permisoVisita.ImportarPermisoVisitaUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvCountUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.inv1.InsertInv1UseCase
import com.portalgm.y_trackcomercial.usecases.ventas.inv1Lotes.InsertInv1LotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios.CountRegistrosListaPreciosUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios.ImportarListaPreciosUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvByDateUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.InsertOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.InsertTransactionOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.UpdateAnularFacturaOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.UpdateFirmaOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.CountUseOinvPendientesCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.ExportarOinvPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.CountOrdenVentaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaCabByIdUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaCabUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaDetUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.ImportarOrdenVentaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.CountRegistrosStockAlmacenUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetDatosDetalleLotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockItemCodeUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockLotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.ImportarStockAlmacenUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.CountRegistrosVendedoresUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.ExportarNroFacturaPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.CountNroFacturaPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetDatosFacturaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetNroFacturaPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetUltimoNroFacturaAzureUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.ImportarVendedoresUseCase
import com.portalgm.y_trackcomercial.usecases.visitas.GetDatosVisitaActivaUseCase
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
    ): GetHorasTranscurridasVisitasUseCase {
        return GetHorasTranscurridasVisitasUseCase(visitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetApiKeyGMSUseCase(
        apiKeyGMSRepository: ApiKeyGMSRepository
    ): GetApiKeyGMSUseCase {
        return GetApiKeyGMSUseCase(apiKeyGMSRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertarNuevaUbicacionUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ): InsertarNuevaUbicacionUseCase {
        return InsertarNuevaUbicacionUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountUbicacionesNuevasPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ): CountUbicacionesNuevasPendientesUseCase {
        return CountUbicacionesNuevasPendientesUseCase(nuevaUbicacionRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetNuevasUbicacionesPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ): GetNuevasUbicacionesPendientesUseCase {
        return GetNuevasUbicacionesPendientesUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportarNuevasUbicacionesPendientesUseCase(
        nuevaUbicacionRepository: NuevaUbicacionRepository
    ): ExportarNuevasUbicacionesPendientesUseCase {
        return ExportarNuevasUbicacionesPendientesUseCase(nuevaUbicacionRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertarNewPassUseCase(
        newPassRepository: NewPassRepository
    ): InsertarNewPassUseCase {
        return InsertarNewPassUseCase(newPassRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountNewPassPendienteUseCase(
        newPassRepository: NewPassRepository
    ): CountNewPassPendienteUseCase {
        return CountNewPassPendienteUseCase(newPassRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetNewPassPendientesUseCase(
        newPassRepository: NewPassRepository
    ): GetNewPassPendientesUseCase {
        return GetNewPassPendientesUseCase(newPassRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportarNewPassPendientesUseCase(
        newPassRepository: NewPassRepository
    ): ExportarNewPassPendientesUseCase {
        return ExportarNewPassPendientesUseCase(newPassRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDatosVisitaActivaUseCase(
        visitasRepository: VisitasRepository
    ): GetDatosVisitaActivaUseCase {
        return GetDatosVisitaActivaUseCase(visitasRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUltimaHoraRegistroUseCase(
        auditTrailRepository: AuditTrailRepository
    ): GetUltimaHoraRegistroUseCase {
        return GetUltimaHoraRegistroUseCase(auditTrailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarParametrosUseCase(
        parametrosRepository: ParametrosRepository
    ): ImportarParametrosUseCase {
        return ImportarParametrosUseCase(parametrosRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetTimerGpsHilo1UseCase(
        parametrosRepository: ParametrosRepository
    ): GetTimerGpsHilo1UseCase {
        return GetTimerGpsHilo1UseCase(parametrosRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarOcrdUseCase(
        OcrdRepository: CustomerRepository
    ): ImportarOcrdUseCase {
        return ImportarOcrdUseCase(OcrdRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOcrdUseCase(
        OcrdRepository: CustomerRepository
    ): GetOcrdUseCase {
        return GetOcrdUseCase(OcrdRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarOcrdUbicacionesUseCase(
        ocrdUbicacionesRepository: OcrdUbicacionesRepository
    ): ImportarOcrdUbicacionesUseCase {
        return ImportarOcrdUbicacionesUseCase(ocrdUbicacionesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOinvUseCase(
        oinvRepository: OinvRepository
    ): GetOinvUseCase {
        return GetOinvUseCase(oinvRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetCountVendedoresUseCase(
        repository: A0_YtvVentasRepository
    ): CountRegistrosVendedoresUseCase {
        return CountRegistrosVendedoresUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarVendedoresUseCase(
        repository: A0_YtvVentasRepository
    ): ImportarVendedoresUseCase {
        return ImportarVendedoresUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarListaPreciosUseCase(
        repository: A0_YTV_LISTA_PRECIOSRepository
    ): ImportarListaPreciosUseCase {
        return ImportarListaPreciosUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountRegistrosListaPreciosUseCase(
        repository: A0_YTV_LISTA_PRECIOSRepository
    ): CountRegistrosListaPreciosUseCase {
        return CountRegistrosListaPreciosUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountRegistrosStockAlmacenUseCase(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): CountRegistrosStockAlmacenUseCase {
        return CountRegistrosStockAlmacenUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarStockAlmacenUseCase(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): ImportarStockAlmacenUseCase {
        return ImportarStockAlmacenUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideImportarOrdenVentaUseCaseUseCase(
        repository: A0_YTV_ORDEN_VENTARepository
    ): ImportarOrdenVentaUseCase {
        return ImportarOrdenVentaUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideCountOrdenVentaUseCase(
        repository: A0_YTV_ORDEN_VENTARepository
    ): CountOrdenVentaUseCase {
        return CountOrdenVentaUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOrdenVentaCabUseCase(
        repository: A0_YTV_ORDEN_VENTARepository
    ): GetOrdenVentaCabUseCase {
        return GetOrdenVentaCabUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOrdenVentaDetUseCase(
        repository: A0_YTV_ORDEN_VENTARepository
    ): GetOrdenVentaDetUseCase {
        return GetOrdenVentaDetUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOrdenVentaCabByIdUseCase(
        repository: A0_YTV_ORDEN_VENTARepository
    ): GetOrdenVentaCabByIdUseCase {
        return GetOrdenVentaCabByIdUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDatosFacturaUseCase(
        repository: A0_YtvVentasRepository
    ): GetDatosFacturaUseCase {
        return GetDatosFacturaUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertOinvUseCase(
        repository: OinvRepository
    ): InsertOinvUseCase {
        return InsertOinvUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertInv1UseCase(
        repository: INV1_REPOSITORY
    ): InsertInv1UseCase {
        return InsertInv1UseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDatosDetalleLotesUseCase(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): GetDatosDetalleLotesUseCase {
        return GetDatosDetalleLotesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertInv1LotesUseCase(
        repository: INV1_LOTES_REPOSITORY
    ): InsertInv1LotesUseCase {
        return InsertInv1LotesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertTransactionOinvUseCase(
        repository: OinvRepository
    ): InsertTransactionOinvUseCase {
        return InsertTransactionOinvUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateFirmaOinvUseCase(
        repository: OinvRepository
    ): UpdateFirmaOinvUseCase {
        return UpdateFirmaOinvUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetOinvByDateUseCase(
        repository: OinvRepository
    ): GetOinvByDateUseCase {
        return GetOinvByDateUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdateAnularFacturaOinvUseCase(
        repository: OinvRepository
    ): UpdateAnularFacturaOinvUseCase {
        return UpdateAnularFacturaOinvUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun providegetOinvPendientesCountUseCase(
        repository: OinvRepository
    ): CountUseOinvPendientesCase {
        return CountUseOinvPendientesCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideExportarOinvPendientesUseCase(
        repository: OinvRepository
    ): ExportarOinvPendientesUseCase {
        return ExportarOinvPendientesUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNroFacturaPendienteUseCase(
        repository: A0_YtvVentasRepository
    ): GetNroFacturaPendienteUseCase {
        return GetNroFacturaPendienteUseCase(repository)
    }
    @Provides
    @ViewModelScoped
    fun provideExportarNroFacturaPendientesUseCase(
        repository: A0_YtvVentasRepository
    ): ExportarNroFacturaPendientesUseCase {
        return ExportarNroFacturaPendientesUseCase(repository)
    }
    @Provides
    @ViewModelScoped
    fun provideGetCountNroFacturaPendienteUseCase(
        repository: A0_YtvVentasRepository
    ): CountNroFacturaPendienteUseCase {
        return CountNroFacturaPendienteUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetUltimoNroFacturaAzureUseCase(
        repository: A0_YtvVentasRepository
    ): GetUltimoNroFacturaAzureUseCase {
        return GetUltimoNroFacturaAzureUseCase(repository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetStockLotesUseCase(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): GetStockLotesUseCase {
        return GetStockLotesUseCase(repository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetStockItemCodeUseCase(
        repository: A0_YTV_STOCK_ALMACENRepository
    ): GetStockItemCodeUseCase {
        return GetStockItemCodeUseCase(repository)
    }





}

