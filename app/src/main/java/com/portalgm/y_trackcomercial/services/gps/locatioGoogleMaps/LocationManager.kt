package com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps

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
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import com.google.android.gms.location.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class LocationManager(
    private val auditTrailRepository: AuditTrailRepository,
    private val sharedPreferences: SharedPreferences,
    private val logRepository: LogRepository,
    private val context: Context
) {
    private var isGpsEnabled = false // Variable para rastrear el estado del GPS
    private val _latitudInsert: MutableLiveData<Double> = MutableLiveData()
    private val _longitudInsert: MutableLiveData<Double> = MutableLiveData()
    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    private val _speed: MutableLiveData<Float> = MutableLiveData()
    private var gpsStateChangeListener: GpsStateChangeListener? = null
    private var locationChangeListener: LocationChangeListener? = null
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private val locationRequest = LocationRequest.create().apply {
      //  priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        interval = 60000
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

        /*    if (latitude != null && longitude != null) {
                locationChangeListener?.onLocationChanged(latitude, longitude)
                // Insertar el log con la nueva ubicación
                Log.d("Location", "Latitud: $latitude, Longitud: $longitude")
                actualizarUbicacion(latitude, longitude, velocidad!!)
            }*/
        }
    }
   /* fun actualizarUbicacion(latitud: Double, longitud: Double, speed: Float) {
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
            /*CoroutineScope(Dispatchers.Main).launch {
                insertRoomLocation()
            }*/
        }
    }*/
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
                val userId = sharedPreferences.getUserId()
                val userName = sharedPreferences.getUserName()

                // Si el estado del GPS cambió de activado a desactivado
                if (isGpsEnabled && !gpsEnabled) {
                    gpsStateChangeListener?.onGpsStateChanged(false)
                    isGpsEnabled = false // Actualizar el estado
                    // Insertar el log una vez
                    val porceBateria = getBatteryPercentage(context!!)
                    CoroutineScope(Dispatchers.Main).launch {

                        try {
                            if(userId>0){
                                LogUtils.insertLog(
                                    logRepository,
                                    LocalDateTime.now().toString(),
                                    "GPS desactivado ",
                                    "Se ha desactivado el GPS",
                                    userId,
                                    userName!!,
                                    "SERVICIO SEGUNDO PLANO",
                                    porceBateria
                                )
                            }
                        } catch (e: Exception) {
                            // Manejo de excepciones aquí, por ejemplo, imprimir un mensaje de error
                            Log.e("CoroutineError", "Error al insertar el log: ${e.message}")
                        }

                    }
                }

                // Si el estado del GPS cambió de desactivado a activado
                 if (!isGpsEnabled && gpsEnabled) {
                    gpsStateChangeListener?.onGpsStateChanged(true)
                    isGpsEnabled = true
                    val porceBateria = getBatteryPercentage(context!!)
                    CoroutineScope(Dispatchers.Main).launch {
                        try {
                        if(userId>0){
                            LogUtils.insertLog(
                                logRepository,
                                LocalDateTime.now().toString(),
                                "GPS activado",
                                "Se ha activado el GPS",
                                userId,
                                userName!!,
                                "SERVICIO SEGUNDO PLANO",
                                porceBateria
                            )
                        }
                        } catch (e: Exception) {
                            // Manejo de excepciones aquí, por ejemplo, imprimir un mensaje de error
                            Log.e("CoroutineError", "Error al insertar el log: ${e.message}")
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
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
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

    private suspend fun insertRoomLocation(
    ) {
        val porceBateria = getBatteryPercentage(context)
        // Aquí puedes usar el auditTrailRepository para guardar la ubicación en Room
        if(sharedPreferences.getUserId().toString() == "0"||sharedPreferences.getUserId().toString().isNullOrBlank()){
            return
        }

        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTimeString = dateFormat.format(currentTime)
        // Verificar si la hora actual está entre las 07:00 y las 18:00
        if (currentTimeString >= "07:00" && currentTimeString <= "19:00") {
            LogUtils.insertLogAuditTrailUtils(
                auditTrailRepository,
                LocalDateTime.now().toString(),
                _longitud.value!!,
                _latitud.value!!,
                sharedPreferences.getUserId(),
                sharedPreferences.getUserName().toString(),
                _speed.value!!.toDouble(),
                porceBateria,"LOCATION MANAGER"
            )
        }
    }

    fun unregisterGpsStateChangeListener() {
        Log.d("RunningServices", "Unregistering GPS state change listener")
        gpsStateChangeListener = null
        context.unregisterReceiver(gpsStateReceiver)
        Log.d("RunningServices", "GPS state change listener unregistered")
    }

    fun registerLocationChangeListener(listener: LocationChangeListener) {
        locationChangeListener = listener
    }
    fun registerGpsStateChangeListener(listener: GpsStateChangeListener) {
        gpsStateChangeListener = listener
    }


}
