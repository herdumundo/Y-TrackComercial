package com.portalgm.y_trackcomercial.services.gps.servicioGMS

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import javax.inject.Inject

class LocationService @Inject constructor(private val context: Context) {
    private var locationCallback: LocationCallBacks? = null // Callback personalizado
    private val fusedLocationClient: FusedLocationProviderClient = FusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        interval = 1000 // Intervalo de actualización de ubicación en milisegundos (10 segundos en este ejemplo)
        fastestInterval = 1000 // Intervalo más rápido en milisegundos
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private val fusedLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            locationCallback?.onLocationUpdated(location!!)
        }
    }
    // Método para configurar el callback personalizado
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, fusedLocationCallback, null)
    }
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(fusedLocationCallback)
    }
    // Método para configurar el callback personalizado
    fun setLocationCallback(callback: LocationCallBacks) {
        locationCallback = callback
    }
}

