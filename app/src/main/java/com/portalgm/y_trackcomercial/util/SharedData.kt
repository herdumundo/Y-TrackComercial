package com.portalgm.y_trackcomercial.util

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    val txtSiedi = MutableLiveData<String>()
    val clase_a_enviarSiedi = MutableLiveData<String>()
    private val _debeContinuar = MutableStateFlow(true)  // Valor inicial
     fun setDebeContinuar(valor: Boolean) {
        _debeContinuar.value = valor
    }
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
