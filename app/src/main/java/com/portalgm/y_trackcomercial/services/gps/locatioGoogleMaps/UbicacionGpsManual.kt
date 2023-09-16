package com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
fun obtenerUbicacionGPSActual(
    locationListener: LocationListener,
    context: Context,
    locationManager:LocationManager)
{

    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    // Actualiza el LiveData en el hilo principal

    if (isGpsEnabled) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                100,
                0f,
                locationListener
            )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
                //      locationViewModel.actualizarUbicacion(it.latitude, it.longitude ,it.speed )
            }

           //

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        finally {
        }
    }
}



