package com.portalgm.y_trackcomercial.services.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import com.portalgm.y_trackcomercial.util.SharedData

class BatteryLevelReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Obtenemos el nivel de batería
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val sharedData = SharedData.getInstance()
        sharedData.porcentajeBateria.value =level
        // Imprimimos el nivel de batería
        Log.d("BatteryLevel", "El nivel de batería es $level")
    }
}
