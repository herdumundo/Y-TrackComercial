package com.portalgm.y_trackcomercial.core.di

import android.app.Application
import android.content.Context
import androidx.annotation.Keep
import com.portalgm.y_trackcomercial.BuildConfig
import com.portalgm.y_trackcomercial.data.api.A0_YTV_LISTA_PRECIOSClient
import com.portalgm.y_trackcomercial.data.api.A0_YTV_ORDEN_VENTAClient
import com.portalgm.y_trackcomercial.data.api.A0_YTV_STOCK_ALMACENClient
import com.portalgm.y_trackcomercial.data.api.A0_YTV_VENDEDORClient
import com.portalgm.y_trackcomercial.data.api.ApiService
import com.portalgm.y_trackcomercial.data.api.DownloadUpdateApiClient
import com.portalgm.y_trackcomercial.data.api.HorariosUsuarioApiClient
import com.portalgm.y_trackcomercial.data.api.LotesListasApiClient
import com.portalgm.y_trackcomercial.data.api.OcrdUbicacionesApiClient
import com.portalgm.y_trackcomercial.data.api.apiResponse.AuthClient
import com.portalgm.y_trackcomercial.data.api.OcrdClient
import com.portalgm.y_trackcomercial.data.api.OcrdOitmClient
import com.portalgm.y_trackcomercial.data.api.OitmClient
import com.portalgm.y_trackcomercial.data.api.ParametrosClient
import com.portalgm.y_trackcomercial.data.api.PermisosVisitasApiClient
import com.portalgm.y_trackcomercial.data.api.UbicacionesPVClient
import com.portalgm.y_trackcomercial.data.api.apiResponse.ApikKeyGMSApi
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionAuditLogApiClient
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionAuditTrailApiClient
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionMovimientosApiClient
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionNewPassApiClient
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionNuevasUbicacionesApiClient
import com.portalgm.y_trackcomercial.data.api.exportaciones.ExportacionVisitasApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Keep

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
  @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

      val timeoutValue = 3L
      return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(timeoutValue, TimeUnit.MINUTES)
                    .readTimeout(timeoutValue, TimeUnit.MINUTES)
                    .writeTimeout(timeoutValue, TimeUnit.MINUTES)
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthClient(retrofit: Retrofit): AuthClient {
        return retrofit.create(AuthClient::class.java)
    }

    @Provides
    @Singleton
    fun provideOCRD(retrofit: Retrofit): OcrdClient {
        return retrofit.create(OcrdClient::class.java)
    }

    @Provides
    @Singleton
    fun provideLotesListas(retrofit: Retrofit): LotesListasApiClient {
        return retrofit.create(LotesListasApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuario(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOcrdUbicaciones(retrofit: Retrofit): OcrdUbicacionesApiClient {
        return retrofit.create(OcrdUbicacionesApiClient::class.java)
    }


    @Provides
    @Singleton
    fun providePermisosVisitas(retrofit: Retrofit): PermisosVisitasApiClient {
        return retrofit.create(PermisosVisitasApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideHorariosUsuario(retrofit: Retrofit): HorariosUsuarioApiClient {
        return retrofit.create(HorariosUsuarioApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideParametros(retrofit: Retrofit): ParametrosClient {
        return retrofit.create(ParametrosClient::class.java)
    }

    @Provides
    @Singleton
    fun provideOitm(retrofit: Retrofit): OitmClient {
        return retrofit.create(OitmClient::class.java)
    }
    @Provides
    @Singleton
    fun provideOcrdOitm(retrofit: Retrofit): OcrdOitmClient {
        return retrofit.create(OcrdOitmClient::class.java)
    }

    @Provides
    @Singleton
    fun provideUbicacionesPv(retrofit: Retrofit): UbicacionesPVClient {
        return retrofit.create(UbicacionesPVClient::class.java)
    }

    @Provides
    @Singleton
    fun provideExportacionVisitas(retrofit: Retrofit): ExportacionVisitasApiClient {
        return retrofit.create(ExportacionVisitasApiClient::class.java)
    }


    @Provides
    @Singleton
    fun provideExportacionAuditTrail(retrofit: Retrofit): ExportacionAuditTrailApiClient {
        return retrofit.create(ExportacionAuditTrailApiClient::class.java)
    }


    @Provides
    @Singleton
    fun provideExportacionAuditLogApi(retrofit: Retrofit): ExportacionAuditLogApiClient {
        return retrofit.create(ExportacionAuditLogApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideExportacionMovimientosApi(retrofit: Retrofit): ExportacionMovimientosApiClient {
        return retrofit.create(ExportacionMovimientosApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideDownloadUpdeteApiClientApi(retrofit: Retrofit): DownloadUpdateApiClient {
        return retrofit.create(DownloadUpdateApiClient::class.java)
    }

    @Provides
    @Singleton
    fun provideApikKeyGMSApiClient(retrofit: Retrofit): ApikKeyGMSApi {
        return retrofit.create(ApikKeyGMSApi::class.java)
    }


    @Provides
    @Singleton
    fun provideExportacionNuevasUbicacionesApiClient(retrofit: Retrofit): ExportacionNuevasUbicacionesApiClient {
        return retrofit.create(ExportacionNuevasUbicacionesApiClient::class.java)
    }


    @Provides
    @Singleton
    fun provideExportacionNewPassApiClient(retrofit: Retrofit): ExportacionNewPassApiClient {
        return retrofit.create(ExportacionNewPassApiClient::class.java)
    }


    @Provides
    @Singleton
    fun provideImportarVendedorApiClient(retrofit: Retrofit): A0_YTV_VENDEDORClient {
        return retrofit.create(A0_YTV_VENDEDORClient::class.java)
    }

    @Provides
    @Singleton
    fun provideImportarListaPreciosApiClient(retrofit: Retrofit): A0_YTV_LISTA_PRECIOSClient {
        return retrofit.create(A0_YTV_LISTA_PRECIOSClient::class.java)
    }

    @Provides
    @Singleton
    fun provideImportarAlmacenStockApiClient(retrofit: Retrofit): A0_YTV_STOCK_ALMACENClient {
        return retrofit.create(A0_YTV_STOCK_ALMACENClient::class.java)
    }
    @Provides
    @Singleton
    fun provideImportarOrdenVentaApiClient(retrofit: Retrofit): A0_YTV_ORDEN_VENTAClient {
        return retrofit.create(A0_YTV_ORDEN_VENTAClient::class.java)
    }




}

