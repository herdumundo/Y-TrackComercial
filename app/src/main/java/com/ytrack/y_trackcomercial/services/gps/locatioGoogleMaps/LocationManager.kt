package com.ytrack.y_trackcomercial.services.gps.locatioGoogleMaps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.ytrack.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.ytrack.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.ytrack.y_trackcomercial.services.battery.getBatteryPercentage
import com.ytrack.y_trackcomercial.util.SharedPreferences
import com.ytrack.y_trackcomercial.util.logUtils.LogUtils
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class LocationManager(
    private val auditTrailRepository: AuditTrailRepository,
    private val sharedPreferences: SharedPreferences,
    private val logRepository: LogRepository,
    private val context: Context
) {
    private var isGpsEnabled = false // Variable para rastrear el estado del GPS

    private val _latitudInsert: MutableLiveData<Double> = MutableLiveData()
    // LiveData mutable para la longitud
    private val _longitudInsert: MutableLiveData<Double> = MutableLiveData()

    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    // LiveData mutable para la longitud
    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    private val _speed: MutableLiveData<Float> = MutableLiveData()



    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000
        fastestInterval = 5000
        smallestDisplacement = 10f
    }
    interface GpsStateChangeListener {
        fun onGpsStateChanged(enabled: Boolean)
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            val latitude = lastLocation?.latitude
            val longitude = lastLocation?.longitude
            val velocidad  = lastLocation?.speed

            if (latitude != null && longitude != null) {
                locationChangeListener?.onLocationChanged(latitude, longitude)
                // Insertar el log con la nueva ubicación
                Log.d("Location", "Latitud: $latitude, Longitud: $longitude")
                actualizarUbicacion(latitude, longitude, velocidad!!)
            }
        }
    }
    fun actualizarUbicacion(latitud: Double, longitud: Double, speed: Float) {
        _latitud.value = latitud
        _longitud.value = longitud
        if (calcularDistancia(
                latitud,
                longitud
            ) >= 50 && (latitud != _latitudInsert.value || longitud != _longitudInsert.value)
        ) {
            _latitudInsert.value = latitud
            _longitudInsert.value = longitud
            _speed.value = speed
            CoroutineScope(Dispatchers.Main).launch {
                insertRoomLocation()
            }
        }
    }
    private fun calcularDistancia(latitud: Double, longitud: Double): Float {
        // Calcular la distancia en metros entre la ubicación actual y la ubicación anterior
        val distancia = FloatArray(1)
        Location.distanceBetween(
            _latitudInsert.value ?: 0.0,
            _longitudInsert.value ?: 0.0,
            latitud,
            longitud,
            distancia
        )
        return distancia[0]
    }

    private val gpsStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                val locationManager =
                    context?.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                val gpsEnabled =
                    locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

                // Si el estado del GPS cambió de activado a desactivado
                if (isGpsEnabled && !gpsEnabled) {
                    gpsStateChangeListener?.onGpsStateChanged(false)
                    isGpsEnabled = false // Actualizar el estado
                    // Insertar el log una vez
                    val porceBateria = getBatteryPercentage(context!!)
                    CoroutineScope(Dispatchers.Main).launch {
                        if(sharedPreferences.getUserId().toString() == "0"||sharedPreferences.getUserId().toString().isNullOrBlank()){

                        }
                        else{

                        LogUtils.insertLog(
                            logRepository,
                            LocalDateTime.now().toString(),
                            "GPS desactivado  Loc",
                            "Se ha desactivado el GPS",
                            sharedPreferences.getUserId(),
                            sharedPreferences.getUserName()!!,
                            "SERVICIO SEGUNDO PLANO",
                            porceBateria
                        )

                        }
                    }
                }

                // Si el estado del GPS cambió de desactivado a activado
                if (!isGpsEnabled && gpsEnabled) {
                    gpsStateChangeListener?.onGpsStateChanged(true)
                    isGpsEnabled = true
                    val porceBateria = getBatteryPercentage(context!!)
                    CoroutineScope(Dispatchers.Main).launch {
                        if(sharedPreferences.getUserId().toString() == "0"||sharedPreferences.getUserId().toString().isNullOrBlank()){

                        }
                        else{
                            LogUtils.insertLog(
                                logRepository,
                                LocalDateTime.now().toString(),
                                "GPS activado Loc",
                                "Se ha activado el GPS",
                                sharedPreferences.getUserId(),
                                sharedPreferences.getUserName()!!,
                                "SERVICIO SEGUNDO PLANO",
                                porceBateria
                            )
                        }

                    }

                }
            }
        }
    }
    init {
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStateReceiver, filter)
    }

    interface LocationChangeListener {
        fun onLocationChanged(latitude: Double, longitude: Double)
        fun onPermissionsDenied()
    }

    private var locationChangeListener: LocationChangeListener? = null

    fun registerLocationChangeListener(listener: LocationChangeListener) {
        locationChangeListener = listener
    }

    fun unregisterLocationChangeListener() {
        locationChangeListener = null
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // ...
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback, // Usar el callback definido
            Looper.getMainLooper()
        )
    }


    private var gpsStateChangeListener: GpsStateChangeListener? = null

    fun registerGpsStateChangeListener(listener: GpsStateChangeListener) {
        gpsStateChangeListener = listener
    }

    fun unregisterGpsStateChangeListener() {
        gpsStateChangeListener = null
        context.unregisterReceiver(gpsStateReceiver)
    }

    private suspend fun insertRoomLocation(
    ) {
        val porceBateria = getBatteryPercentage(context)

        // Aquí puedes usar el auditTrailRepository para guardar la ubicación en Room
        if(sharedPreferences.getUserId().toString() == "0"||sharedPreferences.getUserId().toString().isNullOrBlank()){
            return
        }
        LogUtils.insertLogAuditTrailUtils(
            auditTrailRepository,
            LocalDateTime.now().toString(),
            _longitud.value!!,
            _latitud.value!!,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName().toString(),
            _speed.value!!.toDouble(),
            porceBateria
        )
    }
}
