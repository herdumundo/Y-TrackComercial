package com.portalgm.y_trackcomercial.services.gps.locationLocal

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.util.SharedData
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

private const val MIN_TIME_INTERVAL: Long =  1000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 0f // Distancia mínima en metros
private var gpsDelay = true
var tiempo = 0
var ejecutarBloque2 = false

/*  if ((calcularDistancia(
          it.latitude, it.longitude, latitudInsert, longitudInsert
      )) >= 50 && (it.latitude != latitudInsert || it.longitude != longitudInsert)
  ) {
      insertRoomLocation(
          it.latitude, it.longitude,
          context, sharedPreferences, auditTrailRepository
      )
      latitudInsert = it.latitude
      longitudInsert = it.longitude

      ejecutarBloque2 = false
  }*/

suspend fun obtenerUbicacionGPS(
    context: Context,
    locationViewModel: LocationLocalViewModel,
    locationListener: LocationListener,
    locationManager: LocationManager,
    sharedPreferences: SharedPreferences,
    auditTrailRepository: AuditTrailRepository
) {
    val sharedData = SharedData.getInstance()

    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    locationViewModel.setGpsEnabled(isGpsEnabled)
    var c = 0;
    var a = 0;
    tiempo += 1
    if (isGpsEnabled && gpsDelay) {
        try {
            // Solicitar actualizaciones de ubicación
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_INTERVAL,
                MIN_DISTANCE,
                locationListener,

            )
            // Obtener la última ubicación conocida del proveedor de GPS
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            // Actualizar el ViewModel con la última ubicación conocida
            lastKnownLocation?.let {
                c++
                if (c == 1) {
                    sharedData.latitudUsuarioActual.value=it.latitude!!
                    sharedData.longitudUsuarioActual.value=it.longitude!!

                    sharedData.distanciaPV.value = calcularDistancia(
                        it.latitude,
                        it.longitude,
                        sharedData.latitudPV.value ?: 0.0,
                        sharedData.longitudPV.value ?: 0.0).toInt()
                    if (tiempo >= 3) {//SE VA A EJECUTAR CADA 3 MINUTOS
                        a++
                        if (a == 1) {
                          if (ejecutarBloque2) {
                                val fechaLong = sharedData.fechaLongGlobal.value ?: System.currentTimeMillis()
                                sharedData.tiempo.value =  ((System.currentTimeMillis() - fechaLong) / 1000).toInt()

                                insertRoomLocation(
                                    it.latitude, it.longitude,
                                    context, sharedPreferences, auditTrailRepository,"EJECUCION POR MINUTOS")
                            }
                        }
                        tiempo = 0
                        sharedData.fechaLongGlobal.value = System.currentTimeMillis()//SE COLOCA EL TIEMPO ACTUAL, CADA 3 PASOS SE VUELVE A ACTUALIZAR
                    }
                    ejecutarBloque2 = true
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    } else {
        if (tiempo >= 20) {
            insertRoomLocation(
                1.0, 1.0,
                context, sharedPreferences, auditTrailRepository,"GPS INACTIVO"
            )
            tiempo = 0
        }
    }
}

private fun calcularDistancia(
    latitud: Double,
    longitud: Double,
    latitudInsert: Double,
    longitudInsert: Double
): Float {
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


fun iniciarCicloObtenerUbicacion(
    context: Context,
    locationViewModel: LocationLocalViewModel,
    locationListener: LocationListener,
    sharedPreferences: SharedPreferences,
    auditTrailRepository: AuditTrailRepository,
    timerHilo1: Int
) {
    var doCoroutine = true
    CoroutineScope(Dispatchers.Main).launch {
        do {
            doCoroutine = false
            gpsDelay = true
           // Log.d("PARAMETRO",timerHilo1.toString())
            delay(timerHilo1.toLong()?:60000) // 1  minutos en milisegundos
            val locationManager =  context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            obtenerUbicacionGPS(
                context,
                locationViewModel,
                locationListener,
                locationManager,
                sharedPreferences,
                auditTrailRepository
            )
            delay(15000) // 15 segundos
            locationManager.removeUpdates(locationListener)
            delay(2000) // 2 segundos en milisegundos
            doCoroutine = true
            gpsDelay = false
        } while (doCoroutine)
    }
}//9 17

suspend fun insertRoomLocation(
    latitud: Double,
    longitud: Double,
    context: Context,
    sharedPreferences: SharedPreferences,
    auditTrailRepository: AuditTrailRepository,
    tipoRegistro:String
) {

    val porceBateria = getBatteryPercentage(context)
    // Aquí puedes usar el auditTrailRepository para guardar la ubicación en Room
    if (sharedPreferences.getUserId().toString() == "0" || sharedPreferences.getUserId().toString()
            .isNullOrBlank()
    ) {
        return
    }

    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val currentTimeString = dateFormat.format(currentTime)
    // Verificar si la hora actual está entre las 06:00 y las 18:00
    if (currentTimeString >= "06:00" && currentTimeString <= "20:00") {
        LogUtils.insertLogAuditTrailUtils(
            auditTrailRepository,
            LocalDateTime.now().toString(),
            longitud,
            latitud,
            sharedPreferences.getUserId(),
            sharedPreferences.getUserName().toString(),
            0.0,
            porceBateria,
            tipoRegistro
        )
    }
}
