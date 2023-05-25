package com.example.y_trackcomercial.core.di

import android.content.Context
import androidx.room.Room
import com.example.y_trackcomercial.model.dao.UsuariosDao
import com.example.y_trackcomercial.model.database.YtrackDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule(){
    @Provides
    fun provideUsuariosDao(todoDatabase: YtrackDatabase):UsuariosDao{
        return todoDatabase.usuariosDao()
    }
    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context):YtrackDatabase{
        return Room.databaseBuilder(appContext,YtrackDatabase::class.java,"YtrackDatabase").build()
    }
}