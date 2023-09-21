package com.portalgm.y_trackcomercial.services.gps

import android.content.Context
import android.location.LocationManager

class GpsStatusChecker(private val context: Context) {
    fun isGpsEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}
