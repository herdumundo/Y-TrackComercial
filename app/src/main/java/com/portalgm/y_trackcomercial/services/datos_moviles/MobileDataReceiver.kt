package com.portalgm.y_trackcomercial.services.datos_moviles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.portalgm.y_trackcomercial.util.SharedData

class MobileDataReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }
            val sharedData = SharedData.getInstance()

            val mobileDataEnabled = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
            val wifiConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

            if (mobileDataEnabled) {
                // Datos móviles habilitados, establece el valor en true
                sharedData.sharedBooleanLiveDataMobile.value = true
            } else sharedData.sharedBooleanLiveDataMobile.value = wifiConnected
        }
    }

}
