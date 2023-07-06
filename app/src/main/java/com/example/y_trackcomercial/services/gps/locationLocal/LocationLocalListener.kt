package com.example.y_trackcomercial.services.gps.locationLocal

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.example.y_trackcomercial.util.SharedPreferences
import com.example.y_trackcomercial.util.logUtils.LogUtils
import com.example.y_trackcomercial.util.logUtils.LogUtils.insertLogAuditTrailUtils
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class LocationLocalListener(
    private val locationViewModel: LocationLocalViewModel,

    ) : LocationListener {

    override fun onLocationChanged(location: Location) {
        val velocidad: Float= location.speed

        val latitude = location.latitude
        val longitude = location.longitude
        locationViewModel.actualizarUbicacion(latitude, longitude ,velocidad)
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
