package com.example.y_trackcomercial

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class YtrackApp:Application(){

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel("running_channel","Running Notifications", NotificationManager.IMPORTANCE_HIGH)
            val notificacionManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificacionManager.createNotificationChannel(channel)
        }
    }
}



