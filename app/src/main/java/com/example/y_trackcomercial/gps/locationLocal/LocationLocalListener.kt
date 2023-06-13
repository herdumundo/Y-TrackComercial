package com.example.y_trackcomercial.gps.locationLocal

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

class LocationLocalListener(private val locationViewModel: LocationLocalViewModel) : LocationListener {

    override fun onLocationChanged(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        locationViewModel.actualizarUbicacion(latitude, longitude)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        // Se llama cuando el estado del proveedor de ubicación cambia (ejemplo: activado, desactivado)
    }

    override fun onProviderEnabled(provider: String) {
        locationViewModel.setGpsEnabled(true) // Actualizar gpsEnabled cuando el proveedor de ubicación se habilita
    }

    override fun onProviderDisabled(provider: String) {
        locationViewModel.setGpsEnabled(false) // Actualizar gpsEnabled cuando el proveedor de ubicación se deshabilita
    }



}
