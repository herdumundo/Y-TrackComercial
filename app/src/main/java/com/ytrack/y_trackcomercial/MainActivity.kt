package com.ytrack.y_trackcomercial

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ytrack.y_trackcomercial.components.InfoDialogUnBoton
import com.ytrack.y_trackcomercial.services.gps.locationLocal.LocationLocalListener
import com.ytrack.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.ytrack.y_trackcomercial.services.gps.locationLocal.obtenerUbicacionGPS
import com.ytrack.y_trackcomercial.services.system.ServicioUnderground
import com.ytrack.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel
import com.ytrack.y_trackcomercial.ui.informeInventario.viewmodel.InformeInventarioViewModel
import com.ytrack.y_trackcomercial.ui.inventario.viewmodel.InventarioViewModel
import com.ytrack.y_trackcomercial.ui.login2.LoginScreen
import com.ytrack.y_trackcomercial.ui.login2.LoginViewModel
import com.ytrack.y_trackcomercial.ui.marcacionPromotora.MarcacionPromotoraViewModel
import com.ytrack.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.ytrack.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
 import com.ytrack.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
import com.ytrack.y_trackcomercial.ui.updateApp.UpdateAppViewModel
import com.ytrack.y_trackcomercial.ui.visitaAuditor.viewmodel.VisitaAuditorViewModel
import com.ytrack.y_trackcomercial.ui.visitaHorasTranscurridas.viewmodel.VisitasHorasTranscurridasViewModel
import com.ytrack.y_trackcomercial.ui.visitaSupervisor.viewmodel.VisitaSupervisorViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.ytrack.y_trackcomercial.ui.rastreoUsuarios.viewmodel.RastreoUsuariosViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationViewModel: LocationLocalViewModel
    private lateinit var locationListener: LocationLocalListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ejecutar scheduleExportWork aquí
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),0)
        }


        locationViewModel = ViewModelProvider(this).get(LocationLocalViewModel::class.java)
        locationListener = LocationLocalListener(locationViewModel)
        // Verificar y solicitar permisos de ubicación si es necesario
        verificarPermisosUbicacion()

        setContent {
            MaterialTheme {
                 GpsAvisoPermisos(locationViewModel)
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
                    updateAppViewModel,visitasHorasTranscurridasViewModel,
                    rastreoUsuariosViewModel
                )
            }
        }



        val servicioUnderground=isServiceRunning(ServicioUnderground::class.java)
        val contService=countRunningServices(this@MainActivity)
        //SI EL SERVICIO NO ESTA ACTIVO
        if (!servicioUnderground) {
            val servicioUndergroundIntent = Intent(this@MainActivity, ServicioUnderground::class.java)
            // El servicio no está en ejecución, iniciarlo
            servicioUndergroundIntent.action = ServicioUnderground.Actions.START.toString()
            ContextCompat.startForegroundService(this@MainActivity, servicioUndergroundIntent)
            val seviceActive=isServiceRunning(ServicioUnderground::class.java)
            Log.d("RunningServices", "SERVICIO NO ESTABA ACTIVO Y ARRANCO: $seviceActive Cantidad: $contService")
        }
        //SI EL SERVICIO ESTA ACTIVO
        else{
            val serviceIntent = Intent(this, ServicioUnderground::class.java)
            stopService(serviceIntent)
            val seviceActive=isServiceRunning(ServicioUnderground::class.java)
            val contServicse=countRunningServices(this@MainActivity)
            Log.d("RunningServices", "SERVICIO ESTABA ACTIVO Y MATO: $seviceActive Cantidad: $contServicse")

            val servicioUndergroundIntent = Intent(this@MainActivity, ServicioUnderground::class.java)
            // El servicio no está en ejecución, iniciarlo
            servicioUndergroundIntent.action = ServicioUnderground.Actions.START.toString()
            ContextCompat.startForegroundService(this@MainActivity, servicioUndergroundIntent)
            val seviceActiveinit=isServiceRunning(ServicioUnderground::class.java)
            Log.d("RunningServices", "SERVICIO VOLVIO A ARRANCAR: $seviceActiveinit Cantidad: $contService")
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


    private fun verificarPermisosUbicacion() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        val permissionGranted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(this, permission) == permissionGranted) {
            // Si se tienen los permisos, iniciar la obtención de la ubicación GPS
            locationViewModel.setGpsIsPermission(true)
            iniciarObtencionUbicacionGPS()
        } else {
            // Si no se tienen los permisos, solicitarlos al usuario
            locationViewModel.setGpsIsPermission(false)
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }
    }

    @SuppressLint("MissingPermission")
    private fun iniciarObtencionUbicacionGPS() {
        // Inicia la obtención de la ubicación GPS
        obtenerUbicacionGPS(this, locationViewModel, locationListener)
    }

    @Composable
    fun GpsAvisoPermisos(locationViewModel: LocationLocalViewModel) {
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
          /*  LaunchedEffect(Unit) {
                locationViewModel.logGps()
            }*/
            // El GPS está deshabilitado, mostrar diálogo o realizar alguna acción
            InfoDialogUnBoton(
                title = "Atención!",
                titleBottom = "Habilitar",
                desc = "Debes habilitar el GPS.",
                R.drawable.icono_exit
            ) {
                val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(settingsIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        verificarPermisosUbicacion()
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

    ) {
    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("login") { LoginScreen(loginViewModel, navController) }
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
                updateAppViewModel,visitasHorasTranscurridasViewModel,rastreoUsuariosViewModel
            )
        }
    }
}