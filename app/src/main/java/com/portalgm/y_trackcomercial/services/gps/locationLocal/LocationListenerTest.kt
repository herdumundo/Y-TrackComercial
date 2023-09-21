package com.portalgm.y_trackcomercial.services.gps.locationLocal


import android.location.Location
import android.location.LocationListener
import android.os.Bundle

    class LocationListenerTest() :
    LocationListener {

    override fun onLocationChanged(location: Location) {

     }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

    override fun onProviderEnabled(provider: String) {


     }

    override fun onProviderDisabled(provider: String) {
     }

}
