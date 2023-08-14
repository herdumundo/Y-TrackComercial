package com.ytrack.y_trackcomercial.services.gps.locationGMS

interface LocationChangeListener {
    fun onLocationChanged(latitude: Double, longitude: Double)
    fun onPermissionsDenied()
}
