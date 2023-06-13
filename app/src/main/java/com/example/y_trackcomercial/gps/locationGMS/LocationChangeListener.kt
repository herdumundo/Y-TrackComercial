package com.example.y_trackcomercial.gps.locationGMS

interface LocationChangeListener {
    fun onLocationChanged(latitude: Double, longitude: Double)
    fun onPermissionsDenied()
}
