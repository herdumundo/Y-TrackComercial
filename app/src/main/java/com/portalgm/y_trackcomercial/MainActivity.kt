package com.portalgm.y_trackcomercial

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.portalgm.y_trackcomercial.components.InfoDialogSinBoton
import com.portalgm.y_trackcomercial.components.InfoDialogUnBoton
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBlutu
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalListener
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.portalgm.y_trackcomercial.services.gps.locationLocal.iniciarCicloObtenerUbicacion
import com.portalgm.y_trackcomercial.services.system.ServicioUnderground
import com.portalgm.y_trackcomercial.ui.cambioPass.viewmodel.CambioPassViewModel
import com.portalgm.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel
import com.portalgm.y_trackcomercial.ui.informeInventario.viewmodel.InformeInventarioViewModel
import com.portalgm.y_trackcomercial.ui.inventario.viewmodel.InventarioViewModel
import com.portalgm.y_trackcomercial.ui.login2.LoginScreen
import com.portalgm.y_trackcomercial.ui.login2.LoginViewModel
import com.portalgm.y_trackcomercial.ui.marcacionPromotora.MarcacionPromotoraViewModel
import com.portalgm.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.portalgm.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
import com.portalgm.y_trackcomercial.ui.nuevaUbicacion.viewmodel.NuevaUbicacionViewModel
import com.portalgm.y_trackcomercial.ui.rastreoUsuarios.viewmodel.RastreoUsuariosViewModel
import com.portalgm.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
import com.portalgm.y_trackcomercial.ui.updateApp.UpdateAppViewModel
import com.portalgm.y_trackcomercial.ui.visitaAuditor.viewmodel.VisitaAuditorViewModel
import com.portalgm.y_trackcomercial.ui.visitaHorasTranscurridas.viewmodel.VisitasHorasTranscurridasViewModel
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.viewmodel.VisitaSupervisorViewModel
import com.portalgm.y_trackcomercial.ui.visitasSinUbicacion.viewmodel.VisitaSinUbicacionViewModel
import com.portalgm.y_trackcomercial.usecases.auditLog.GetUltimaHoraRegistroUseCase
import com.portalgm.y_trackcomercial.usecases.parametros.GetTimerGpsHilo1UseCase
import com.portalgm.y_trackcomercial.usecases.visitas.GetDatosVisitaActivaUseCase
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val tablasRegistradasViewModel: TablasRegistradasViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels()
    private val marcacionPromotoraViewModel: MarcacionPromotoraViewModel by viewModels()
    private val inventarioViewModel: InventarioViewModel by viewModels()
    private val informeInventarioViewModel: InformeInventarioViewModel by viewModels()
    private val visitaSupervisorViewModel: VisitaSupervisorViewModel by viewModels()
    private val exportacionViewModel: ExportacionViewModel by viewModels()
    private val visitaAuditorViewModel: VisitaAuditorViewModel by viewModels()
    private val updateAppViewModel: UpdateAppViewModel by viewModels()
    private val visitasHorasTranscurridasViewModel: VisitasHorasTranscurridasViewModel by viewModels()
    private val rastreoUsuariosViewModel: RastreoUsuariosViewModel by viewModels()
    private val nuevaUbicacionViewModel: NuevaUbicacionViewModel by viewModels()
    private val cambioPassViewModel: CambioPassViewModel by viewModels()
    private val visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel by viewModels()

    private val updateType = AppUpdateType.IMMEDIATE
    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )// Agrega esto para "Allow all the time"
    private lateinit var locationViewModel: LocationLocalViewModel
    private lateinit var locationListener: LocationLocalListener
    private lateinit var appUpdateManager: AppUpdateManager

     @Inject
    lateinit var auditTrailRepository: AuditTrailRepository
    @Inject
    lateinit var logRepository: LogRepository
    @Inject
    lateinit var getUltimaHoraRegistroUseCase: GetUltimaHoraRegistroUseCase
    @Inject
    lateinit var getDatosVisitaActivaUseCase: GetDatosVisitaActivaUseCase
    @Inject
    lateinit var getTimerGpsHilo1UseCase: GetTimerGpsHilo1UseCase

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var indiceGrupoPermisos = 0

     // Índice del permiso actual en el grupo
    private var indicePermisoEnGrupo = 0
    val sharedData = SharedData.getInstance()

    var timerGpsHilo1=60000
    private val REQUEST_READ_PHONE_STATE = 1

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedData.sharedBooleanLiveData.observe(this) { isGpsEnabled ->
            locationViewModel.setGpsEnabled(isGpsEnabled)
        }
        //VER CUANDO DESINSTALO LA APP FALLA
        GlobalScope.launch(Dispatchers.Main) {

            var datosVisita=getDatosVisitaActivaUseCase.getDatosVisitaActivaUseCase()
            sharedData.idVisitaGlobal.value = datosVisita[0].idVisita
            sharedData.latitudPV.value = datosVisita[0].latitudPV
            sharedData.longitudPV.value = datosVisita[0].longitudPV
            sharedData.fechaLongGlobal.value=getUltimaHoraRegistroUseCase.GetUltimaHoraRegistroUseCase()
            timerGpsHilo1=getTimerGpsHilo1UseCase.getInt()

            iniciarObtencionUbicacionGPS()

        }

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)


        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.registerListener(installStateUpdateListener)
        }
        checkForAppUpdates()
        // Ejecutar scheduleExportWork aquí
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        //}


        locationViewModel = ViewModelProvider(this).get(LocationLocalViewModel::class.java)
        locationListener = LocationLocalListener(locationViewModel)
        // Verificar y solicitar permisos de ubicación si es necesario
        setContent {
            val context = LocalContext.current
            val servicioUnderground = isServiceRunning(ServicioUnderground::class.java)
            val contService = countRunningServices(this@MainActivity)
            //SI EL SERVICIO NO ESTA ACTIVO
            if (!servicioUnderground) {
                val servicioUndergroundIntent =
                    Intent(this@MainActivity, ServicioUnderground::class.java)
                // El servicio no está en ejecución, iniciarlo
                servicioUndergroundIntent.action = ServicioUnderground.Actions.START.toString()
                ContextCompat.startForegroundService(this@MainActivity, servicioUndergroundIntent)
                val seviceActive = isServiceRunning(ServicioUnderground::class.java)
                Log.d(
                    "RunningServices",
                    "SERVICIO NO ESTABA ACTIVO Y ARRANCO: $seviceActive Cantidad: $contService"
                )
            }
            else{
                val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currentHour in 5..16)//HACER QUE SOLO MATE E INICIE SERVICIO HASTA LAS 11, ESTO HARA QUE NO SE CONECTE CADA RATO AL WEBSOCKET.
                {
                    //SI EL SERVICIO ESTA ACTIVO
                    //  else {
                    val serviceIntent = Intent(this, ServicioUnderground::class.java)
                    stopService(serviceIntent)
                    val seviceActive = isServiceRunning(ServicioUnderground::class.java)
                    val contServicse = countRunningServices(this@MainActivity)
                    Log.d(
                        "RunningServices",
                        "SERVICIO ESTABA ACTIVO Y MATO: $seviceActive Cantidad: $contServicse"
                    )

                    val servicioUndergroundIntent =
                        Intent(this@MainActivity, ServicioUnderground::class.java)
                    // El servicio no está en ejecución, iniciarlo
                    servicioUndergroundIntent.action = ServicioUnderground.Actions.START.toString()
                    ContextCompat.startForegroundService(this@MainActivity, servicioUndergroundIntent)
                    val seviceActiveinit = isServiceRunning(ServicioUnderground::class.java)
                    Log.d(
                        "RunningServices",
                        "SERVICIO VOLVIO A ARRANCAR: $seviceActiveinit Cantidad: $contService"
                    )
                    //   }
                }
            }

            var dialogAvisoInternet by remember { mutableStateOf(true) }
            AppScreen(context = context, this)
            MaterialTheme {
                GpsAvisoPermisos(locationViewModel, dialogAvisoInternet)
                val navController = rememberNavController()
                Router(
                    navController,
                    loginViewModel,
                    menuPrincipalViewModel,
                    tablasRegistradasViewModel,
                    locationViewModel,
                    marcacionPromotoraViewModel,
                    inventarioViewModel,
                    informeInventarioViewModel,
                    visitaSupervisorViewModel,
                    exportacionViewModel,
                    visitaAuditorViewModel,
                    updateAppViewModel,
                    visitasHorasTranscurridasViewModel,
                    rastreoUsuariosViewModel,
                    nuevaUbicacionViewModel,
                    cambioPassViewModel,
                    sharedPreferences,
                    visitaSinUbicacionViewModel
                )
            }
        }
        solicitarPermisos()
        /*val bluetoothPrinter = servicioBlutu()
        bluetoothPrinter.printBluetooth(this)*/
    }

    private val gruposDePermisos = listOf(
        Pair(
            arrayOf(
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            ), 111
        ),
        Pair(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 222
        ),
        Pair(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_BASIC_PHONE_STATE,
                Manifest.permission.READ_PHONE_NUMBERS,
                ), 333
        ),
        Pair(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.FOREGROUND_SERVICE
            ), 444
        )
    )


    private fun solicitarPermisos() {
        if (indiceGrupoPermisos < gruposDePermisos.size) {
            val (permisos, codigoSolicitud) = gruposDePermisos[indiceGrupoPermisos]
            val permisoActual = permisos[indicePermisoEnGrupo]

            if (ContextCompat.checkSelfPermission(
                    this,
                    permisoActual
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // El permiso actual no está concedido, solicítalo al usuario
                ActivityCompat.requestPermissions(this, arrayOf(permisoActual), codigoSolicitud)
            } else {
                // El permiso actual está concedido
                // Continuar con el siguiente permiso dentro del mismo grupo
                indicePermisoEnGrupo++
                if (indicePermisoEnGrupo == permisos.size) {
                    // Todos los permisos en el grupo actual están concedidos
                    // Continuar con el siguiente grupo de permisos
                    indiceGrupoPermisos++
                    indicePermisoEnGrupo = 0
                }
                solicitarPermisos()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }

        /*   try {
               socket?.close()
           } catch (e: IOException) {
               // Manejar el error de cierre del socket
           }*/
    }
    // Un método para iniciar la impresión
    fun printData() {

    }
    private fun redirectToLocationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        //ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun checkForAppUpdates() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable && isUpdateAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType, this, 123
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Verificar si el permiso solicitado se concedió
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // El permiso se concedió, continuar solicitando permisos si es necesario
            solicitarPermisos()
        } else {
            // El permiso no se concedió, manejar la situación en consecuencia (por ejemplo, mostrar un mensaje al usuario)
        }
    }

    override fun onResume() {
        super.onResume()
        // verificarPermisosUbicacion()
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType, this, 123
                    )
                }
            }

        }
        val permissionsGranted = locationPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionsGranted) {
            Toast.makeText(
                this,
                "Debes dar permiso de GPS como 'Permitir todo el tiempo' ",
                Toast.LENGTH_LONG
            ).show()
            redirectToLocationSettings()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            println("HUBO UN ERROR")
        }
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

    @SuppressLint("MissingPermission")
    private fun iniciarObtencionUbicacionGPS() {
        // Inicia la obtención de la ubicación GPS
        //   obtenerUbicacionGPS(this, locationViewModel, locationListener)
        iniciarCicloObtenerUbicacion(
            this,
            locationViewModel,
            locationListener,
            sharedPreferences,
            auditTrailRepository,
            timerGpsHilo1
        )
    }

    @Composable
    fun GpsAvisoPermisos(locationViewModel: LocationLocalViewModel, dialogAvisoInternet: Boolean) {
        val gpsEnabled by locationViewModel.gpsEnabled.observeAsState()
        val gpsIsPermission by locationViewModel.gpsIsPermission.observeAsState()

        if (gpsIsPermission == false) {
            // El permiso de GPS está deshabilitado, mostrar el diálogo de información
            InfoDialogUnBoton(
                title = "Atención!",
                titleBottom = "Habilitar",
                desc = "Debes habilitar los permisos.",
                R.drawable.icono_exit
            ) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }

        if (gpsEnabled == false) {
            InfoDialogUnBoton(
                title = "Acceso a la Ubicación",
                titleBottom = "Permitir Acceso",
                desc = "Debes habilitar el GPS para usar la APP",
                R.drawable.icono_exit
            ) {
                val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(settingsIntent)
            }

        }
    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(applicationContext, "Aplicacion descargada con exito", Toast.LENGTH_LONG)
                .show()
            lifecycleScope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }
}


@Composable
fun Router(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    menuPrincipalViewModel: MenuPrincipalViewModel,
    tablasRegistradasViewModel: TablasRegistradasViewModel,
    locationViewModel: LocationLocalViewModel,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
    inventarioViewModel: InventarioViewModel,
    informeInventarioViewModel: InformeInventarioViewModel,
    visitaSupervisorViewModel: VisitaSupervisorViewModel,
    exportacionViewModel: ExportacionViewModel,
    visitaAuditorViewModel: VisitaAuditorViewModel,
    updateAppViewModel: UpdateAppViewModel,
    visitasHorasTranscurridasViewModel: VisitasHorasTranscurridasViewModel,
    rastreoUsuariosViewModel: RastreoUsuariosViewModel,
    nuevaUbicacionViewModel: NuevaUbicacionViewModel,
    cambioPassViewModel: CambioPassViewModel,
    sharedPreferences: SharedPreferences,
    visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel,

    ) {
    NavHost(
        navController = navController,
        startDestination = "menu"
    )
    {
        composable("menu") {
            MenuPrincipal(
                loginViewModel,
                navController,
                menuPrincipalViewModel,
                tablasRegistradasViewModel,
                locationViewModel,
                marcacionPromotoraViewModel,
                inventarioViewModel,
                informeInventarioViewModel,
                visitaSupervisorViewModel,
                exportacionViewModel,
                visitaAuditorViewModel,
                updateAppViewModel,
                visitasHorasTranscurridasViewModel,
                rastreoUsuariosViewModel,
                nuevaUbicacionViewModel,
                cambioPassViewModel,
                sharedPreferences,
                visitaSinUbicacionViewModel
            )
        }
        composable("login") { LoginScreen(loginViewModel, navController) }

    }
}

@Composable
fun AppScreen(context: Context, LifecycleOwner: LifecycleOwner) {
    /*
        var dialogInternet=false
        val sharedData = SharedData.getInstance()

        sharedData.sharedBooleanLiveDataMobile.observe(LifecycleOwner) { isDataMobileEnabled ->
            dialogInternet=isDataMobileEnabled
        }*/

    val sharedData = SharedData.getInstance()
    val isDataMobileEnabled by sharedData.sharedBooleanLiveDataMobile.observeAsState(true)


    if (!isDataMobileEnabled) {
        InfoDialogSinBoton(
            title = "Aviso de Conexión a Internet",
            desc = "La aplicación Ytrack Comercial necesita acceso a Internet para funcionar correctamente y proporcionar sus servicios.",
            R.drawable.bolt_uix_no_internet
        ) {
            // Handle dialog button click
        }
    }
}






