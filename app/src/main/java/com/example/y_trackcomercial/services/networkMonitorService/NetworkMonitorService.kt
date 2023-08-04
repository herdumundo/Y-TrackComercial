package com.example.y_trackcomercial.services.networkMonitorService

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.IBinder
import android.util.Log

class NetworkMonitorService : Service() {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate() {
        super.onCreate()
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = createNetworkCallback()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Registra el NetworkCallback para recibir notificaciones
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Desregistra el NetworkCallback al detener el servicio
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // La red está disponible (puede ser una red WiFi, paquete de datos, etc.)
            Log.i("Mensaje","La red está disponible (puede ser una red WiFi, paquete de datos, etc.)")

        }

        override fun onLost(network: Network) {
            // La conexión a la red se ha perdido (puede ser una red WiFi, paquete de datos, etc.)
            Log.i("Mensaje","La conexión a la red se ha perdido (puede ser una red WiFi, paquete de datos, etc.")
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            // Las capacidades de la red han cambiado (por ejemplo, puede cambiar de WiFi a paquete de datos o viceversa)
            val isMobileDataEnabled = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            if (isMobileDataEnabled) {
                // El paquete de datos está activo
            } else {
                // El paquete de datos está desactivado
            }
        }
    }
}
