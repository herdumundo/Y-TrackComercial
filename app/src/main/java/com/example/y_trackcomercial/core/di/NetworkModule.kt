package com.example.y_trackcomercial.core.di

 import android.app.Application
 import android.content.Context
 import com.example.y_trackcomercial.ui.login.data.network.LoginClient
 import com.example.y_trackcomercial.ui.login2.data.network.AuthClient
 import com.example.y_trackcomercial.util.DataManager
 import dagger.Module
 import dagger.Provides
 import dagger.hilt.InstallIn
 import dagger.hilt.android.qualifiers.ApplicationContext
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
            .baseUrl("http://192.168.6.126:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginClient(retrofit: Retrofit):LoginClient{
        return retrofit.create(LoginClient::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthClient(retrofit: Retrofit):AuthClient{
        return retrofit.create(AuthClient::class.java)
    }



 }

