package com.example.y_trackcomercial.services.gps.locationLocal

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager

private const val MIN_TIME_INTERVAL: Long = 5000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 5f // Distancia mínima en metros

//@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun obtenerUbicacionGPS(context: Context, locationViewModel: LocationLocalViewModel, locationListener: LocationListener) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    locationViewModel.setGpsEnabled(isGpsEnabled)

    if (isGpsEnabled) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE,
                locationListener
            )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
                locationViewModel.actualizarUbicacion(it.latitude, it.longitude)
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}

