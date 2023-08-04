package com.example.y_trackcomercial.services.system
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
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
    @Inject
    lateinit var logRepository: LogRepository

   /* private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
*/
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
     //   connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    //    networkCallback = createNetworkCallback()
        // Crear el canal de notificación para Android 8.0 (Oreo) y versiones posteriores
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            val channelId = "servicio_channel"
            val channelName = "Servicio Channel"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, "servicio_channel")
            .setContentTitle("Ytrack activo")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        startForeground(1, notification)

        val locationListener = LocationLocalListenerService(auditTrailRepository, sharedPreferences, logRepository, this)
        obtenerUbicacionGPSService(this, locationListener)
        // Registra el NetworkCallback
     //   connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    enum class Actions {
        START, STOP
    }
    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            val isMobileDataEnabled = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            if (isMobileDataEnabled) {
                // El paquete de datos está activo
                Log.i("Mensaje", "El paquete de datos está activo")
            } else {
                // El paquete de datos está desactivado
                Log.i("Mensaje", "El paquete de datos está desactivado")
            }
        }
    }
}
