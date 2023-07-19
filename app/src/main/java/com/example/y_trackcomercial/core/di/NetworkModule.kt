package com.example.y_trackcomercial.core.di

import android.app.Application
import android.content.Context
import com.example.y_trackcomercial.data.network.ApiService
import com.example.y_trackcomercial.data.network.HorariosUsuarioApiClient
import com.example.y_trackcomercial.data.network.LotesListasApiClient
import com.example.y_trackcomercial.data.network.OcrdUbicacionesApiClient
import com.example.y_trackcomercial.ui.login2.data.network.AuthClient
import com.example.y_trackcomercial.data.network.OcrdClient
import com.example.y_trackcomercial.data.network.OcrdOitmClient
import com.example.y_trackcomercial.data.network.OitmClient
import com.example.y_trackcomercial.data.network.ParametrosClient
import com.example.y_trackcomercial.data.network.PermisosVisitasApiClient
import com.example.y_trackcomercial.data.network.UbicacionesPVClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val timeoutValue = 30L
        return Retrofit.Builder()
             .baseUrl("https://app-ytrack-b.wonderfulisland-f986ad78.eastus2.azurecontainerapps.io/")
            // .baseUrl("http://192.168.6.134:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(timeoutValue, TimeUnit.SECONDS)
                    .readTimeout(timeoutValue, TimeUnit.SECONDS)
                    .writeTimeout(timeoutValue, TimeUnit.SECONDS)
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
}

