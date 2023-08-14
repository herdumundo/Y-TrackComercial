package com.ytrack.y_trackcomercial

import android.app.ActivityManager
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.ytrack.y_trackcomercial.services.system.ServicioUnderground
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YtrackApp:Application(){

    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT<= Build.VERSION_CODES.TIRAMISU){
            val channel = NotificationChannel("running_channel","Running Notifications", NotificationManager.IMPORTANCE_HIGH)
            val notificacionManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificacionManager.createNotificationChannel(channel)
        }

    /*    val runningServicesCount = countRunningServices(this)
        Log.d("RunningServices", "Number of running services: $runningServicesCount")

      //  if (!isServiceRunning(ServicioUnderground::class.java)) {
            val servicioUndergroundIntent = Intent(this@YtrackApp, ServicioUnderground::class.java)
             servicioUndergroundIntent.action = ServicioUnderground.Actions.START.toString()
            ContextCompat.startForegroundService(this@YtrackApp, servicioUndergroundIntent)
       // }
        val runningServicesCount2 = countRunningServices(this)

        Log.d("RunningServices", "Number of running services: $runningServicesCount2")*/
    }
    fun countRunningServices(context: Context): Int {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Integer.MAX_VALUE)
        return runningServices.size
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}



