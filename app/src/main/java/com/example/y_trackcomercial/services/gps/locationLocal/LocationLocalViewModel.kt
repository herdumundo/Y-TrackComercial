package com.example.y_trackcomercial.services.gps.locationLocal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationLocalViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData mutable para la latitud
    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    val latitud: LiveData<Double> = _latitud

    // LiveData mutable para la longitud
    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    val longitud: LiveData<Double> = _longitud

    // LiveData mutable para el estado del GPS
    private val _gpsEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val gpsEnabled: LiveData<Boolean> = _gpsEnabled

    // LiveData mutable para el estado del permiso de GPS
    private val _gpsIsPermission: MutableLiveData<Boolean> = MutableLiveData()
    val gpsIsPermission: LiveData<Boolean> = _gpsIsPermission

    // Función para establecer el estado del GPS habilitado
    fun setGpsEnabled(enabled: Boolean) {
        _gpsEnabled.value = enabled
    }

    // Función para actualizar las coordenadas de latitud y longitud
    fun actualizarUbicacion(latitud: Double, longitud: Double) {
        _latitud.value = latitud
        _longitud.value = longitud
    }

    // Función para establecer el estado del permiso de GPS
    fun setGpsIsPermission(permission: Boolean) {
        _gpsIsPermission.value = permission
    }

}


// Obtiene una instancia del FusedLocationProviderClient usando el contexto de la aplicación
// private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

// Bloque init, se ejecuta al crear una instancia del ViewModel

//Función para obtener la ubicación GPS
/* @SuppressLint("MissingPermission")
 fun obtenerUbicacionGPS() {
     // Verifica si el permiso de GPS está habilitado
     if (_gpsIsPermission.value == true) {
         // Obtiene la última ubicación conocida
         fusedLocationClient.lastLocation
             .addOnSuccessListener { location ->
                 location?.let {
                     // Actualiza las coordenadas de latitud y longitud
                     actualizarUbicacion(location.latitude, location.longitude)
                 }
             }
     }
 }*/