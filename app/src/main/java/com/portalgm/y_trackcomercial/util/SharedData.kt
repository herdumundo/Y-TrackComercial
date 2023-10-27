package com.portalgm.y_trackcomercial.util

import androidx.lifecycle.MutableLiveData

class SharedData {
    val sharedBooleanLiveData = MutableLiveData<Boolean>()
    val sharedBooleanLiveDataMobile = MutableLiveData<Boolean>()
    val porcentajeBateria = MutableLiveData<Int>()

    val idVisitaGlobal = MutableLiveData<Long>()
    val latitudPV = MutableLiveData<Double>()
    val longitudPV = MutableLiveData<Double>()
    val latitudUsuarioActual = MutableLiveData<Double>()
    val longitudUsuarioActual = MutableLiveData<Double>()
    val distanciaPV = MutableLiveData<Int>()
    val tiempo = MutableLiveData<Int>()
    val fechaLongGlobal = MutableLiveData<Long>()
    val webSocketConectado = MutableLiveData<Boolean>()

    companion object {
        @Volatile
        private var instance: SharedData? = null

        fun getInstance(): SharedData {
            return instance ?: synchronized(this) {
                instance ?: SharedData().also { instance = it }
            }
        }
    }
}
