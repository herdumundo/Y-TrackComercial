package com.example.y_trackcomercial.services.gps.locationLocal

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.util.SharedPreferences
import com.example.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
 import javax.inject.Inject


@HiltViewModel
class LocationLocalViewModel @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository,
    private val sharedPreferences: SharedPreferences,
    ) : ViewModel() {

    // LiveData mutable para la latitud
    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    val latitud: LiveData<Double> = _latitud

    // LiveData mutable para la longitud
    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    val longitud: LiveData<Double> = _longitud


    private val _latitudInsert: MutableLiveData<Double> = MutableLiveData()
    val latitudInsert: LiveData<Double> = _latitudInsert

    // LiveData mutable para la longitud
    private val _longitudInsert: MutableLiveData<Double> = MutableLiveData()
    val longitudInsert: LiveData<Double> = _longitudInsert

    private val _speed: MutableLiveData<Float> = MutableLiveData()

    // LiveData mutable para el estado del GPS
    private val _gpsEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val gpsEnabled: LiveData<Boolean> = _gpsEnabled

    // LiveData mutable para el estado del permiso de GPS
    private val _gpsIsPermission: MutableLiveData<Boolean> = MutableLiveData()
    val gpsIsPermission: LiveData<Boolean> = _gpsIsPermission

    fun setGpsEnabled(enabled: Boolean) {
        _gpsEnabled.value = enabled
    }

    private suspend fun insertRoomLocation() {
        val latitud = _latitud.value ?: return
        val longitud = _longitud.value ?: return
        val speed = _speed.value ?: return

        // Verificar si los valores son nulos y regresar si lo son
        if (latitud == null || longitud == null || speed == null || sharedPreferences.getUserId() == null || sharedPreferences.getUserName() == null) {
            return
        }
        LogUtils.insertLogAuditTrailUtils(
            auditTrailRepository,
            LocalDateTime.now().toString(),
            latitud,
            longitud,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName()!!,
            speed.toDouble()
        )
    }

    // Función para actualizar las coordenadas de latitud y longitud
  /*  fun actualizarUbicacion(latitud: Double, longitud: Double, speed: Float) {
        _latitud.value = latitud
        _longitud.value = longitud
        if (calcularDistancia(latitud, longitud) >= 50  && (latitud != _latitudInsert.value || longitud != _longitudInsert.value)) {
            _latitudInsert.value = latitud
            _longitudInsert.value = longitud
            _speed.value = speed

            viewModelScope.launch {
                insertRoomLocation()
            }
        }
    }*/
    // Función para establecer el estado del permiso de GPS
    fun setGpsIsPermission(permission: Boolean) {
        _gpsIsPermission.value = permission
    }

    private fun calcularDistancia(latitud: Double, longitud: Double): Float {
        // Calcular la distancia en metros entre la ubicación actual y la ubicación anterior
        val distancia = FloatArray(1)
        Location.distanceBetween(
            _latitudInsert.value ?: 0.0,
            _longitudInsert.value ?: 0.0,
            latitud,
            longitud,
            distancia
        )
        return distancia[0]
    }
}