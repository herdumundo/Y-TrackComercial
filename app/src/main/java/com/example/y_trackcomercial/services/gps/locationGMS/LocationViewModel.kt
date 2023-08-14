package com.example.y_trackcomercial.services.gps.locationGMS


import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    application: Application,
   // private var locationService: LocationService
) : AndroidViewModel(application) {

    private val context: Context = application
   // private val locationServiceIntent = Intent(application, LocationService::class.java)

    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    val latitud: LiveData<Double> = _latitud

    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    val longitud: LiveData<Double> = _longitud

    private val _isGpsEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    val isGpsEnabled: LiveData<Boolean> = _isGpsEnabled

    private val _isPermissionGPS: MutableLiveData<Boolean> = MutableLiveData(true)
    val isPermissionGPS: LiveData<Boolean> = _isPermissionGPS


    private val locationChangeListener = object : LocationChangeListener {
        override fun onLocationChanged(latitude: Double, longitude: Double) {
            _latitud.value = latitude
            _longitud.value = longitude
            _isPermissionGPS.value = true
        }

        override fun onPermissionsDenied() {
            // No es necesario pasar la latitud y longitud en este caso
            _latitud.value = null
            _longitud.value = null
            _isPermissionGPS.value = false
        }
    }
/*
    private val locationServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            val locationService = binder.getService()
            locationService.registerLocationChangeListener(locationChangeListener)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // Manejar la desconexión del servicio si es necesario
        }
    }*/

    init {
     //   context.bindService(
           // locationServiceIntent,
           // locationServiceConnection,
        //    Context.BIND_AUTO_CREATE
       // )
    }

    override fun onCleared() {
        super.onCleared()
     //   getApplication<Application>().unbindService(locationServiceConnection)
    }

   /* fun startLocationService() {
        context.startService(locationServiceIntent)
        context.bindService(
            locationServiceIntent,
            locationServiceConnection,
            Context.BIND_AUTO_CREATE
        )
        getLocationService()?.registerLocationChangeListener(locationChangeListener)
    }*/

  /*  fun stopLocationService() {
        context.stopService(locationServiceIntent)
        getLocationService()?.unregisterLocationChangeListener()
    }*/

    fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            restartLocationService()
        } else {
            locationChangeListener.onPermissionsDenied()
        }
    }

    private fun restartLocationService() {
       // stopLocationService()
       // startLocationService()
        checkGpsEnabled()
    }

    fun checkGpsEnabled() {
        // Verificar el estado del GPS y actualizar _isGpsEnabled
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        _isGpsEnabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    fun setGpsEnabled(enabled: Boolean) {
        _isGpsEnabled.value = enabled
    }

}