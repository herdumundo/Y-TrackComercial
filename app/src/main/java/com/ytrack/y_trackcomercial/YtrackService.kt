package com.ytrack.y_trackcomercial
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import java.util.Calendar
import android.util.Log

class YtrackService : Service() {

    private val INTERVAL_MINUTES = 30 // Intervalo en minutos

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Obtener el AlarmManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Crear un intent para iniciar el servicio
        val serviceIntent = Intent(this, YtrackService::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, serviceIntent, -1)

        // Calcular la hora de inicio para la próxima alarma
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, INTERVAL_MINUTES)

        // Programar la alarma para repetirse cada 30 minutos
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            (INTERVAL_MINUTES * 60 * 1000).toLong(), // Convertir minutos a milisegundos
            pendingIntent
        )

        // Agregar un registro de log para indicar que se programó la alarma
        Log.d("YtrackService", "Alarma programada para repetirse cada $INTERVAL_MINUTES minutos")

        // Indicar que el servicio debe ser reiniciado si se detiene
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
