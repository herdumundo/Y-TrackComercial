package com.example.y_trackcomercial.services.system

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.example.y_trackcomercial.services.exportacion.ExportarDatos
import com.example.y_trackcomercial.services.gps.locationLocal.LocationLocalListenerService
import com.example.y_trackcomercial.services.gps.locationLocal.obtenerUbicacionGPSService
import com.example.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.example.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.example.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.example.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.example.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.example.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.example.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.example.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
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

    @Inject
    lateinit var countCantidadPendientes: CountCantidadPendientes

    @Inject
    lateinit var countAuditTrailUseCase: CountAuditTrailUseCase

    @Inject
    lateinit var countLogPendientesUseCase: CountLogPendientesUseCase

    @Inject
    lateinit var countMovimientoUseCase: CountMovimientoUseCase

    @Inject
    lateinit var getVisitasPendientesUseCase: GetVisitasPendientesUseCase

    @Inject
    lateinit var getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase

    @Inject
    lateinit var getLogPendienteUseCase: GetLogPendienteUseCase

    @Inject
    lateinit var getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase

    @Inject
    lateinit var enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase

    @Inject
    lateinit var enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase

    @Inject
    lateinit var enviarLogPendientesUseCase: EnviarLogPendientesUseCase

    @Inject
    lateinit var enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase


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
        ExportarDatos(
            countAuditTrailUseCase,
            countCantidadPendientes,
            countLogPendientesUseCase,
            countMovimientoUseCase,
            getVisitasPendientesUseCase,
            getAuditTrailPendienteUseCase,
            getLogPendienteUseCase,
            getMovimientoPendientesUseCase,
            enviarVisitasPendientesUseCase,
            enviarAuditTrailPendientesUseCase,
            enviarLogPendientesUseCase,
            enviarMovimientoPendientesUseCase
        )

        //   connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //    networkCallback = createNetworkCallback()
        // Crear el canal de notificación para Android 8.0 (Oreo) y versiones posteriores
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.TIRAMISU) {
            val channelId = "servicio_channel"
            val channelName = "Servicio Channel"
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(this, "servicio_channel")
            .setContentTitle("Ytrack activo")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()
        startForeground(1, notification)

        val locationListener = LocationLocalListenerService(
            auditTrailRepository,
            sharedPreferences,
            logRepository,
            this
        )
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

}
