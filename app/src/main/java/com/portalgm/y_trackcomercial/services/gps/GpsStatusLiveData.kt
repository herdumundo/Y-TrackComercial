package com.portalgm.y_trackcomercial.services.gps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.lifecycle.LiveData

class GpsStatusLiveData(private val context: Context) : LiveData<Boolean>() {

    private val gpsStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            value = isGpsEnabled()
        }
    }

    override fun onActive() {
        super.onActive()
        // Registra el receptor de difusión para el cambio en el estado del GPS
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusReceiver, filter)
        // Actualiza el valor inicial
        value = isGpsEnabled()
    }

    override fun onInactive() {
        super.onInactive()
        // Desregistra el receptor de difusión cuando no está en uso
        context.unregisterReceiver(gpsStatusReceiver)
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
