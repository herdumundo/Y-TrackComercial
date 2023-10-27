package com.portalgm.y_trackcomercial.services.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log
import com.portalgm.y_trackcomercial.util.SharedData
class GpsStateReceiver() : BroadcastReceiver() {
    private var isGpsEnabledPreviously = false

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action == "android.location.PROVIDERS_CHANGED") {
            val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isGpsEnabled != isGpsEnabledPreviously) {
                val sharedData = SharedData.getInstance()
                sharedData.sharedBooleanLiveData.value =isGpsEnabled
                Log.d("GpsStateReceiver",  sharedData.sharedBooleanLiveData.value.toString())
                isGpsEnabledPreviously = isGpsEnabled
            }
        }
    }
}