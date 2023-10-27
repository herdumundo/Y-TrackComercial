package com.portalgm.y_trackcomercial.services.gps.servicioGMS

import android.location.Location

interface LocationCallBacks {
    fun onLocationUpdated(location: Location)
}
