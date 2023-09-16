

package com.portalgm.y_trackcomercial.services.gps.locationLocal

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

private const val MIN_TIME_INTERVAL: Long = 1000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 10f // Distancia mínima en metros
private  var gpsDelay=true
var latitudInsert=0.0
var longitudInsert=0.0
  suspend fun obtenerUbicacionGPS(
      context: Context,
      locationViewModel: LocationLocalViewModel,
      locationListener: LocationListener,
      locationManager: LocationManager,
      sharedPreferences:  SharedPreferences,
      auditTrailRepository: AuditTrailRepository
  )
 {
    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    locationViewModel.setGpsEnabled(isGpsEnabled)
     var c=0;

     if (isGpsEnabled&&gpsDelay) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE,
                locationListener )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
            c++
                if(c==1){
                    if ((calcularDistancia(
                            it.latitude,
                            it.longitude,latitudInsert,longitudInsert
                        ) ) >= 50 && (it.latitude != latitudInsert  || it.longitude != longitudInsert)
                    ){
                        insertRoomLocation(it.latitude, it.longitude ,
                            context,sharedPreferences,auditTrailRepository)
                        latitudInsert=it.latitude
                        longitudInsert=it.longitude
                    }
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
private fun calcularDistancia(latitud: Double, longitud: Double,latitudInsert:Double,longitudInsert:Double): Float {
    // Calcular la distancia en metros entre la ubicación actual y la ubicación anterior
    val distancia = FloatArray(1)
    Location.distanceBetween(
        latitudInsert,
        longitudInsert,
        latitud,
        longitud,
        distancia
    )
    return distancia[0]
}


  fun iniciarCicloObtenerUbicacion(context: Context,
                                   locationViewModel: LocationLocalViewModel,
                                   locationListener: LocationListener,
                                   sharedPreferences:  SharedPreferences,
                                   auditTrailRepository: AuditTrailRepository)
  {
      var doCoroutine=true
      CoroutineScope(Dispatchers.Main).launch {
        do {
            doCoroutine=false
            gpsDelay=true
            delay(240000) // 4 minutos en milisegundos
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            obtenerUbicacionGPS(context, locationViewModel, locationListener, locationManager,sharedPreferences,auditTrailRepository)
            delay(15000) // 15 segundos
            locationManager.removeUpdates(locationListener)
            delay(5000) // 5 segundos en milisegundos
            doCoroutine=true
            gpsDelay=false
        }
        while (doCoroutine)
    }
}

private suspend fun insertRoomLocation(
    latitud:Double,
    longitud:Double,
    context: Context,
    sharedPreferences:  SharedPreferences,
    auditTrailRepository: AuditTrailRepository
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
            longitud ,
            latitud ,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName().toString()+"1",
            0.0,
            porceBateria
        )
    }
}


/*package com.portalgm.y_trackcomercial.services.gps.locationLocal

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val MIN_TIME_INTERVAL: Long = 1000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 10f // Distancia mínima en metros
private  var gpsDelay=true
private const val INITIAL_DELAY: Long = 120000 // Retardo inicial de 2 minutos en milisegundos
private const val UPDATE_DURATION: Long = 10000 // Duración de las actualizaciones de 10 segundos en milisegundos

 fun obtenerUbicacionGPS(context: Context, locationViewModel: LocationLocalViewModel, locationListener: LocationListener) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    locationViewModel.setGpsEnabled(isGpsEnabled)

    if (isGpsEnabled) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE,
                locationListener
            )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
          //      locationViewModel.actualizarUbicacion(it.latitude, it.longitude ,it.speed )
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
} */


