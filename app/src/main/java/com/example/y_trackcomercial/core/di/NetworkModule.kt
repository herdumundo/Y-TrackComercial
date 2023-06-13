package com.example.y_trackcomercial.core.di

  import android.app.Application
 import android.content.Context
  import com.example.y_trackcomercial.data.network.ApiService
 import com.example.y_trackcomercial.data.network.LotesListasApiClient
  import com.example.y_trackcomercial.data.network.OcrdUbicacionesApiClient
  import com.example.y_trackcomercial.ui.login2.data.network.AuthClient
 import com.example.y_trackcomercial.data.network.OcrdClient
  import dagger.Module
 import dagger.Provides
 import dagger.hilt.InstallIn
 import dagger.hilt.components.SingletonComponent
 import retrofit2.Retrofit
 import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
          //  .baseUrl("https://yemsys0606--qw4qx64.braverock-93fcb381.westus2.azurecontainerapps.io/")
            .baseUrl("http://192.168.6.126:7000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    @Singleton
    @Provides
    fun provideAuthClient(retrofit: Retrofit):AuthClient{
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
    fun provideOcrdUbicaciones(retrofit: Retrofit): OcrdUbicacionesApiClient{
        return retrofit.create(OcrdUbicacionesApiClient::class.java)
    }

 }

