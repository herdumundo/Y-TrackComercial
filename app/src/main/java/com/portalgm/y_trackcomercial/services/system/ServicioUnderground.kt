package com.portalgm.y_trackcomercial.services.system

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import com.portalgm.y_trackcomercial.BuildConfig
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.LotesListasRepository
import com.portalgm.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.OitmRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.battery.BatteryLevelReceiver
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.services.datos_moviles.MobileDataReceiver
import com.portalgm.y_trackcomercial.services.exportacion.ExportarDatos
import com.portalgm.y_trackcomercial.services.gps.locationLocal.insertRoomLocation
import com.portalgm.y_trackcomercial.services.websocket.PieSocketListener
import com.portalgm.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDateTime
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
    @Inject
    lateinit var enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase
    @Inject
    lateinit var getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase
    @Inject
    lateinit var countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase
    @Inject
    lateinit var lotesListasRepository: LotesListasRepository
    @Inject
    lateinit var ocrdUbicacionesRepository: OcrdUbicacionesRepository
    @Inject
    lateinit var OcrdOitmRepository: OcrdOitmRepository
    @Inject
    lateinit var oitmRepository: OitmRepository
    @Inject
    lateinit var customerRepository: CustomerRepository
    @Inject
    lateinit var pieSocketListener: PieSocketListener

    private var previousDataMobileState: Boolean? = null
    private lateinit var context: Context // Declaración de la propiedad de clase para el contexto
    private val gpsStateReceiver = GpsStateReceiver()
    private val mobileDataReceiver = MobileDataReceiver()
    val filter = IntentFilter("android.location.PROVIDERS_CHANGED")
    val sharedData = SharedData.getInstance()
    val batteryLevelReceiver = BatteryLevelReceiver()
    var latitudViejaWebSocket =0.0
    var bateriaEach=0 //SE USA PARA QUE AL ABRIR LA APP NUEVAMENTE NO INSERTE EL LOG DE BATERIA.
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate()
    {
        super.onCreate()
       // sharedData.debeContinuar.value=true
        context = applicationContext // Asignación del contexto en onCreate
        registerReceiver(gpsStateReceiver, filter)
        val filterDataMobile = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mobileDataReceiver, filterDataMobile)
        //BATERIA SERVICIO
        context.registerReceiver(batteryLevelReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        sharedData.sharedBooleanLiveData.observeForever(gpsEnabledObserver)
        sharedData.sharedBooleanLiveDataMobile.observeForever(mobileDataEnabledObserver)
        sharedData.porcentajeBateria.observeForever(bateriaObserver)
        sharedData.latitudUsuarioActual.observeForever(latitudObserver)

        if(sharedPreferences.getUserId()>0){
            //SI EXISTE UN USUARIO INGRESADO PERMITIR.
            // Crea una instancia de PieSocketListener
            pieSocketListener = PieSocketListener(customerRepository,lotesListasRepository,ocrdUbicacionesRepository,OcrdOitmRepository,oitmRepository,sharedPreferences)
            pieSocketListener.connectToServer(BuildConfig.BASE_URL_WEB_SOCKET)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val context = applicationContext
        //DESTRUIMOS EL OBSERVADORFOREVER PARA QUE NO HAGA INSERT DUPLICADOS.
        sharedData.sharedBooleanLiveData.removeObserver(gpsEnabledObserver)
        sharedData.sharedBooleanLiveDataMobile.removeObserver(mobileDataEnabledObserver)
        sharedData.porcentajeBateria.removeObserver(bateriaObserver)
        unregisterReceiver(mobileDataReceiver)
        context.unregisterReceiver(batteryLevelReceiver)
        unregisterReceiver(gpsStateReceiver)
        sharedData.latitudUsuarioActual.removeObserver(latitudObserver)

        if (pieSocketListener != null) {
            pieSocketListener.closeWebSocket() // Agrega un método para cerrar la conexión WebSocket si no lo tienes ya implementado
        }

        //Log.d("RunningServices", "Matamos el servicio")
        // Desregistramos el receptor de difusión
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
    return super.onStartCommand(intent, flags, startId)
        //     return START_STICKY
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
            enviarMovimientoPendientesUseCase,
            enviarNuevasUbicacionesPendientesUseCase,
            getNuevasUbicacionesPendientesUseCase,
            countUbicacionesNuevasPendientesUseCase  )
        // Crear el canal de notificación para Android 8.0 (Oreo) y versiones posteriores
       try {


        if (android.os.Build.VERSION.SDK_INT <= 34) {
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

       }  catch (e: Exception) {
            Log.i("MensajeError",e.toString())
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    enum class Actions {
        START, STOP
    }

    private val latitudObserver = Observer<Double> { nuevaLatitud ->
        // Verificar si el WebSocket está abierto
        if (pieSocketListener.isConnected())
        {
            /*  if(nuevaLatitud!=latitudViejaWebSocket)
              {*/
            // Crear un objeto JSON con los nuevos datos
            val data = JSONObject()
            data.put("id", sharedPreferences.getUserId())
            data.put("latitud", nuevaLatitud)
            data.put("longitud", sharedData.longitudUsuarioActual.value ?: 0.0)
            val message = data.toString()
            pieSocketListener.enviarCoordenadas(message)
            // }
            latitudViejaWebSocket=nuevaLatitud
        }
    }
    private val bateriaObserver = Observer<Int> { cambioPorcentaje ->
        //val context = applicationContext
        GlobalScope.launch {
            if(bateriaEach>1){
                if(sharedPreferences.getUserId()>0){
                    insertRoomLocation(
                        1.0, 1.0,
                        context, sharedPreferences, auditTrailRepository,"BATERIA"
                    )
                }
            }
            bateriaEach++
        }
    }
    private val gpsEnabledObserver = Observer<Boolean> { isGpsEnabled ->
        GlobalScope.launch {
            if(sharedPreferences.getUserId()>0)
            {
                val porceBateria = getBatteryPercentage(this@ServicioUnderground)
                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "GPS $isGpsEnabled",
                    isGpsEnabled.toString(),
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName()!!,
                    "SERVICIO SEGUNDO PLANO",
                    porceBateria
                )
            }
        }
    }
    private val mobileDataEnabledObserver = Observer<Boolean> { isDataMobileEnabled ->
        if (previousDataMobileState != isDataMobileEnabled) {
            if(sharedPreferences.getUserId()>0){
                GlobalScope.launch {
                    val porceBateria = getBatteryPercentage(this@ServicioUnderground)
                    LogUtils.insertLog(
                        logRepository,
                        LocalDateTime.now().toString(),
                        "Paquete de datos $isDataMobileEnabled",
                        isDataMobileEnabled.toString(),
                        sharedPreferences.getUserId(),
                        sharedPreferences.getUserName()!!,
                        "SERVICIO SEGUNDO PLANO",
                        porceBateria
                    )
                }
            }
            previousDataMobileState = isDataMobileEnabled
        }
    }
}