package com.example.y_trackcomercial.util

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class DataManager(context: Context) {

    private val dataStore = context.createDataStore("data_prefs")

    companion object{
        val USER_TOKEN = preferencesKey<String>("USER_TOKEN")

    }

    suspend fun storeData(token:String){
        dataStore.edit {
            it[USER_TOKEN] = token

        }
    }
    val userNameFlow:Flow<String> = dataStore.data.map {
        it[USER_TOKEN] ?: ""
    }




}


