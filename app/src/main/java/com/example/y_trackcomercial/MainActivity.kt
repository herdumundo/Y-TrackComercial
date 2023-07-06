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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.y_trackcomercial.components.InfoDialogUnBoton
import com.example.y_trackcomercial.services.gps.locationLocal.LocationLocalListener
import com.example.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.example.y_trackcomercial.services.gps.locationLocal.obtenerUbicacionGPS
import com.example.y_trackcomercial.ui.login2.LoginScreen
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import com.example.y_trackcomercial.ui.marcacionPromotora.MarcacionPromotoraViewModel
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipal
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel
import com.example.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
import com.example.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationViewModel: LocationLocalViewModel
    private lateinit var locationListener: LocationLocalListener
    private val loginViewModel: LoginViewModel by viewModels()
    private val tablasRegistradasViewModel: TablasRegistradasViewModel by viewModels()
    private val menuPrincipalViewModel: MenuPrincipalViewModel by viewModels()
    private val marcacionPromotoraViewModel: MarcacionPromotoraViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    marcacionPromotoraViewModel
                )
            }
        }
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
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    )
    {
        composable("login") { LoginScreen(loginViewModel, navController) }
        composable("menu") {
            MenuPrincipal(
                loginViewModel, navController, menuPrincipalViewModel,
                tablasRegistradasViewModel, locationViewModel,marcacionPromotoraViewModel
            )
        }
    }
}