package com.example.y_trackcomercial.services.gps.locationLocal

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.y_trackcomercial.MainActivity
import com.example.y_trackcomercial.R

private const val MIN_TIME_INTERVAL: Long = 5000 // Intervalo de tiempo mínimo en milisegundos (ejemplo: 1000ms = 1 segundo)
private const val MIN_DISTANCE: Float = 10f // Distancia mínima en metros

 fun obtenerUbicacionGPSService(context: Context,  locationListener: LocationListener) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    // Verificar si el proveedor de GPS está habilitado
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)


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
                //locationViewModel.actualizarUbicacion(it.latitude, it.longitude ,it.speed )
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

     else {
        // Log.i("mensaje","GPS DESACTIVADO")
        showGpsDisabledNotification(context)
    }



}

@SuppressLint("UnspecifiedImmutableFlag")
private fun showGpsDisabledNotification(context: Context) {
    val channelId = "gps_disabled_channel"
    val channelName = "GPS Desactivado"

    val notificationIntent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("Atención!")
        .setContentText("Debes habilitar el GPS.")
        .setSmallIcon(R.drawable.icono_exit) // Reemplaza "ic_warning" con el nombre de tu icono de notificación
        .setContentIntent(pendingIntent)
        .build()

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(2, notification)
}

