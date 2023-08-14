package com.example.y_trackcomercial.services.gps.locationGMS

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.example.y_trackcomercial.util.SharedPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LocationService @Inject constructor(
    private val auditTrailRepository: AuditTrailRepository,
    private val sharedPreferences: SharedPreferences,
    private val logRepository: LogRepository,
    private val context: Context

) : Service() {

    // Binder para permitir la conexión al servicio desde un componente externo
    private val binder: IBinder = LocationBinder()

    // Listener para recibir las actualizaciones de ubicación
    private var locationChangeListener: LocationChangeListener? = null

    // Cliente para acceder a los servicios de ubicación de Google Play
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Configuración de la solicitud de ubicación
    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // Intervalo de tiempo en milisegundos entre actualizaciones
        fastestInterval = 5000 // Intervalo de tiempo más rápido en milisegundos
        smallestDisplacement =
            10f // Distancia mínima en metros para considerar un cambio de ubicación
    }

    // Binder para permitir la conexión al servicio desde un componente externo
    inner class LocationBinder : Binder() {
        fun getService(): LocationService {
            return this@LocationService
        }
    }

    // Callback para recibir las actualizaciones de ubicación
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            val latitude = lastLocation?.latitude
            val longitude = lastLocation?.longitude
            if (latitude != null && longitude != null) {
                locationChangeListener?.onLocationChanged(latitude, longitude)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLocationUpdates()
        return START_STICKY
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Si no se tienen los permisos necesarios, se notifica al ViewModel
            locationChangeListener?.onPermissionsDenied()
            Log.i("EstadoGPS", "NO TIENE PERMISO")
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun registerLocationChangeListener(listener: LocationChangeListener) {
        locationChangeListener = listener
    }

    // Método para anular el registro del LocationChangeListener
    fun unregisterLocationChangeListener() {
        locationChangeListener = null
    }
}
