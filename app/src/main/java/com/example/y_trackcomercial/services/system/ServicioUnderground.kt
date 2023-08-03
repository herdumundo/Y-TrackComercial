package com.example.y_trackcomercial.services.system

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.services.gps.locationLocal.LocationLocalListenerService
import com.example.y_trackcomercial.services.gps.locationLocal.obtenerUbicacionGPSService
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class ServicioUnderground : Service() {

    @Inject
    lateinit var auditTrailRepository: AuditTrailRepository
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Crea una notificación para que el servicio se ejecute en primer plano

        when(intent?.action){
            Actions.START.toString()->start()
            Actions.STOP.toString() ->stopSelf()
        }
         return super.onStartCommand(intent, flags, startId)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun start() {
        val notification = NotificationCompat.Builder(this, "running_channel")
            .setContentTitle("Ytrack activo")
           // .setContentText("Ejecutando tareas")
            .setSmallIcon(R.mipmap.ic_launcher) // Reemplaza "ic_launcher" con el nombre de tu icono de notificación
            .build()
            startForeground(1, notification)

         var locationListener =LocationLocalListenerService(auditTrailRepository,sharedPreferences)

         obtenerUbicacionGPSService(this, locationListener)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    enum class Actions {
        START,STOP
    }
    private fun startLocationUpdates() {
        // Llama al método que inicia las actualizaciones de ubicación en LocationLocalListenerService
     }


}
