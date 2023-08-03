package com.example.y_trackcomercial.services.gps.locationLocal

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class LocationLocalListener(private val locationViewModel: LocationLocalViewModel) :
    LocationListener {

    override fun onLocationChanged(location: Location) {
        val velocidad: Float = location.speed
        val latitude = location.latitude
        val longitude = location.longitude
     // locationViewModel.actualizarUbicacion(latitude, longitude, velocidad)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onProviderEnabled(provider: String) {
        locationViewModel.setGpsEnabled(true) // Actualizar gpsEnabled cuando el proveedor de ubicación se habilita
    }

    override fun onProviderDisabled(provider: String) {
        locationViewModel.setGpsEnabled(false) // Actualizar gpsEnabled cuando el proveedor de ubicación se deshabilita
    }

}
