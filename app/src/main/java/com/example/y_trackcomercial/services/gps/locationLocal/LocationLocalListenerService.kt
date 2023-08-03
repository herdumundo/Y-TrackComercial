package com.example.y_trackcomercial.services.gps.locationLocal

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.util.SharedPreferences
import com.example.y_trackcomercial.util.logUtils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class LocationLocalListenerService(
    private val auditTrailRepository: AuditTrailRepository,
    private val sharedPreferences: SharedPreferences,

    ) :
    LocationListener {
    private val _latitudInsert: MutableLiveData<Double> = MutableLiveData()

    // LiveData mutable para la longitud
    private val _longitudInsert: MutableLiveData<Double> = MutableLiveData()

    private val _latitud: MutableLiveData<Double> = MutableLiveData()

    // LiveData mutable para la longitud
    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    private val _speed: MutableLiveData<Float> = MutableLiveData()


    override fun onLocationChanged(location: Location) {
        val velocidad: Float = location.speed
        val latitude = location.latitude
        val longitude = location.longitude
        // Launch a coroutine and call insertRoomLocation from within the coroutine
        actualizarUbicacion(latitude, longitude, velocidad)
        /*CoroutineScope(Dispatchers.Main).launch {
                insertRoomLocation(latitude, longitude, velocidad)
            } */
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onProviderEnabled(provider: String) {
        // locationViewModel.setGpsEnabled(true) // Actualizar gpsEnabled cuando el proveedor de ubicación se habilita
    }

    override fun onProviderDisabled(provider: String) {
        //locationViewModel.setGpsEnabled(false) // Actualizar gpsEnabled cuando el proveedor de ubicación se deshabilita
    }


    private suspend fun insertRoomLocation(
    ) {

        // Aquí puedes usar el auditTrailRepository para guardar la ubicación en Room
        LogUtils.insertLogAuditTrailUtils(
            auditTrailRepository,
            LocalDateTime.now().toString(),
            _longitud.value!!,
            _latitud.value!!,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName().toString() + "T",
            _speed.value!!.toDouble()
        )
    }


    fun actualizarUbicacion(latitud: Double, longitud: Double, speed: Float) {
        _latitud.value = latitud
        _longitud.value = longitud
        if (calcularDistancia(
                latitud,
                longitud
            ) >= 50 && (latitud != _latitudInsert.value || longitud != _longitudInsert.value)
        ) {
            _latitudInsert.value = latitud
            _longitudInsert.value = longitud
            _speed.value = speed
            CoroutineScope(Dispatchers.Main).launch {
                insertRoomLocation()

            }

         }
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
