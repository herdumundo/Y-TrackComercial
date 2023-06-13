package com.example.y_trackcomercial

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.example.y_trackcomercial.components.InfoDialogUnBoton
import com.example.y_trackcomercial.gps.calculoMetrosPuntosGps
import com.example.y_trackcomercial.gps.locationLocal.LocationLocalListener
import com.example.y_trackcomercial.gps.locationLocal.LocationLocalViewModel
import com.example.y_trackcomercial.gps.locationLocal.obtenerUbicacionGPS
import com.example.y_trackcomercial.ui.login2.LoginScreen
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
import com.example.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationViewModel: LocationLocalViewModel
    private lateinit var locationListener: LocationLocalListener
    private val loginViewModel: LoginViewModel by viewModels()
    private val tablasRegistradasViewModel: TablasRegistradasViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationViewModel = ViewModelProvider(this).get(LocationLocalViewModel::class.java)
        locationListener = LocationLocalListener(locationViewModel)

        setContent {
            MaterialTheme {
              //  GpsLocationScreen(locationViewModel)
                val navController = rememberNavController()
                Router(navController, loginViewModel,menuPrincipalViewModel,tablasRegistradasViewModel)
            }
        }
        // Verificar y solicitar permisos de ubicación si es necesario
        verificarPermisosUbicacion()
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
    fun GpsLocationScreen(locationViewModel: LocationLocalViewModel) {
        val latitud         by locationViewModel.latitud.observeAsState()
        val longitud        by locationViewModel.longitud.observeAsState()
        val gpsEnabled      by locationViewModel.gpsEnabled.observeAsState()
        val gpsIsPermission by locationViewModel.gpsIsPermission.observeAsState()

             if (gpsIsPermission==false) {
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
       Column() {
            Text(text = "Latitud: ${latitud ?: "-"}")
            Text(text = "Longitud: ${longitud ?: "-"}")

            if(latitud!=null && longitud!=null   ){

            }
            Text(text = "Metros: ${ calculoMetrosPuntosGps(latitud ?: 0.0, longitud ?: 0.0) }")

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
    tablasRegistradasViewModel: TablasRegistradasViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("login") { LoginScreen(loginViewModel, navController) }
        composable("menu") { MenuPrincipal(loginViewModel, navController, menuPrincipalViewModel,tablasRegistradasViewModel) }
        //  composable("menu") { sendWhatsAppMessage() }
    }
}
/*

package com.example.y_trackcomercial

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.y_trackcomercial.components.InfoDialogUnBoton
import com.example.y_trackcomercial.gps.LocationViewModel
import com.example.y_trackcomercial.gps.GpsStatusReceiver
import com.example.y_trackcomercial.gps.locationLocal.LocationLocalViewModel
import com.example.y_trackcomercial.gps.locationLocal.obtenerUbicacionGPS
import com.example.y_trackcomercial.ui.login2.LoginScreen
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var gpsStatusReceiver: GpsStatusReceiver
    private val loginViewModel: LoginViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels()
   // private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.checkPermissions()
        locationViewModel.checkGpsEnabled()
        gpsStatusReceiver = GpsStatusReceiver(locationViewModel)
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        registerReceiver(gpsStatusReceiver, filter)*/

// Uso de la función obtenerUbicacionGPS
       // val ubicacion = obtenerUbicacionGPS(this)

          lateinit var locationViewModel: LocationLocalViewModel


        setContent {
            MaterialTheme {

              /*  val isGpsEnabled by locationViewModel.isGpsEnabled.observeAsState()
                val isPermissionGPS by locationViewModel.isPermissionGPS.observeAsState()
*/
                val latitud = locationViewModel.latitud.value.toString()
                val longitud by locationViewModel.longitud.observeAsState()

           /*     if (isGpsEnabled==false) {
                    InfoDialogUnBoton(
                    title = "Atenciòn!",
                    titleBottom = "Habilitar",
                    desc = "Debes habilitar el GPS.",
                    R.drawable.icono_exit
                ) {
                        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(settingsIntent)
                }
                }

              if (isPermissionGPS==false ) {
                    InfoDialogUnBoton(
                        title = "Atenciòn!",
                        titleBottom = "Abrir configuración",
                        desc = "Debes habilitar los permisos para el GPS.",
                        R.drawable.icono_exit
                    ) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                   }
                }*/

             /*  val navController = rememberNavController()
                Router(navController, loginViewModel,menuPrincipalViewModel)
*/

               val modelo = Build.MODEL
                val marca = Build.MANUFACTURER
               // val imei = getIMEI(this)
              //  val nroTelefono= getNumber(this)

                val context = LocalContext.current
                locationViewModel.iniciarObtencionUbicacion(context)
                  //  if (ubicacion != null) {

                        Text(text = "Latitud: ${latitud ?: "-"}")
                        Text(text = "Longitud: ${longitud ?: "-"}")

                        // Utiliza la latitud y longitud obtenidas según sea necesario
                  /*  } else {
                        // No se pudo obtener la ubicación del GPS o el proveedor de GPS está deshabilitado
                    }*/

                    Text(text = "Modelo: $modelo")
                    Text(text = "Marca: $marca")
                   // Text(text = "Nro: $nroTelefono")
                 //   Text(text = "IMEI: $imei")
                }
            }

    }
    override fun onResume() {
        super.onResume()
       // locationViewModel.checkPermissions()

    }

    override fun onPause() {
        super.onPause()
     }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(gpsStatusReceiver)
    }

}
@Composable
fun Router(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    menuPrincipalViewModel: MenuPrincipalViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { LoginScreen(loginViewModel, navController) }
        composable("menu") { MenuPrincipal(loginViewModel, navController, menuPrincipalViewModel) }
        //  composable("menu") { sendWhatsAppMessage() }


    }
}

 /*
    private val loginViewModel: LoginViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels()
    private val applicationViewModel: ApplicationViewModel by viewModels<ApplicationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val location by applicationViewModel.getLocationLiveData().observeAsState()

                val serviceIntent = Intent(this, LocationService::class.java)
                startService(serviceIntent)


                /* val navController = rememberNavController()
                 Router(navController, loginViewModel,menuPrincipalViewModel)*/

            //    GpsLocation()

            }
        }
    }

}


@SuppressLint("MissingPermission")
@Composable
fun GpsLocation() {
    val showEnableGpsDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val locationManager =  LocalContext.current.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
    var locationEnabled = remember { mutableStateOf(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) }

  //  val permissionState = remember { mutableStateOf(false) }



    val context = LocalContext.current
    val permissionState = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        while (true) {
            val hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            permissionState.value = hasPermission
            delay(1000) // Actualizar el estado cada segundo (ajusta el valor según tus necesidades)
        }
    }
        if(permissionState.value) {
        Log.i("permiso","Tengo permiso en este momento")
        }
        else {
            Log.i("permiso","No Tengo permiso en este momento")
        }





    // Observar los cambios en el estado del GPS y actualizar locationEnabled
    DisposableEffect(Unit) {
        val gpsStatusListener = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                locationEnabled.value = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }
        }

        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(gpsStatusListener, filter)

        onDispose {
            context.unregisterReceiver(gpsStatusListener)
        }
    }


    if(!locationEnabled.value){

    InfoDialogUnBoton(
        title = "Atenciòn!",
        titleBottom = "Activar GPS",
        desc = "Debes habilitar el GPS.",
        R.drawable.icono_exit
    ) {
        showEnableGpsDialog.value = false
        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(settingsIntent)
    }

}

      if (!permissionState.value ) {
        InfoDialogUnBoton(
            title = "Atenciòn!",
            titleBottom = "Abrir configuración",
            desc = "Debes habilitar los permisos para el GPS.",
            R.drawable.icono_exit
        ) {
            showEnableGpsDialog.value = false
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(settingsIntent)
        }
    }
    if(permissionState.value &&locationEnabled.value ) {


        val latitude = remember { mutableStateOf(0.0) }
        val longitude = remember { mutableStateOf(0.0) }

        val locationListener = remember {
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    latitude.value = location.latitude
                    longitude.value = location.longitude
                }

                override fun onProviderDisabled(provider: String) {}

                override fun onProviderEnabled(provider: String) {}

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            }
        }

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10f, locationListener
            )

            onDispose {
                locationManager.removeUpdates(locationListener)
            }
        }

        Column {
            Text("Latitud: ${latitude.value}")
            Text("Longitud: ${longitude.value}")
        }
    }
}


}
 */

 */