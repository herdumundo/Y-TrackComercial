package com.portalgm.y_trackcomercial.ui.marcacionPromotora

 import android.content.Context
 import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
 import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
 import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
 import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
 import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogOk
 import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
 import java.util.regex.Pattern


 import android.widget.Toast
 import androidx.compose.foundation.shape.CircleShape
 import androidx.compose.material.BottomAppBar
 import androidx.compose.material.Scaffold
 import androidx.compose.material.SnackbarHost
 import androidx.compose.material.icons.filled.PunchClock
 import androidx.compose.material.icons.filled.TrackChanges
 import androidx.compose.material.rememberScaffoldState
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.platform.LocalContext
 import com.portalgm.y_trackcomercial.components.BottomMenuItem
 import com.portalgm.y_trackcomercial.components.MyBottomBar
 import com.portalgm.y_trackcomercial.data.model.models.OcrdItem

@Composable
fun GpsLocationScreen(
    locationViewModel: LocationLocalViewModel,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel
) {
    val context = LocalContext.current.applicationContext
    val context2 = LocalContext.current

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        snackbarHost = { SnackbarHost(it) },
        bottomBar = {
            // Agregar un ícono para el menú desplegable
            BottomAppBar(
                backgroundColor = Color(0xFF000000),
                cutoutShape = CircleShape, // Cambiar a CircleShape si quieres un botón flotante
                content = { MyBottomBar( prepareBottomMenu(context2,marcacionPromotoraViewModel))
                })

        },
        content = {

            val latitudUsuario by locationViewModel.latitud.observeAsState()
            val longitudUsuario by locationViewModel.longitud.observeAsState()
            val metros by marcacionPromotoraViewModel.metros.observeAsState()
            val showDialog by marcacionPromotoraViewModel.showDialog.observeAsState(false)
            val mensajeDialog by marcacionPromotoraViewModel.mensajeDialog.observeAsState("")

            LaunchedEffect(Unit) {
                marcacionPromotoraViewModel.consultaVisitaActiva()
                marcacionPromotoraViewModel.getAddresses()
                marcacionPromotoraViewModel.inicializarValores()
                marcacionPromotoraViewModel.obtenerUbicacion()
            }
            MyApp(marcacionPromotoraViewModel, latitudUsuario ?: 0.0, longitudUsuario ?: 0.0)

            if (showDialog) {
                InfoDialogOk(
                    title = "Atención",
                    desc = mensajeDialog,
                    image = R.drawable.bolt_uix_no_internet,
                    funcion = { marcacionPromotoraViewModel.cerrarDialogMensaje() }) {
                }
            }
        }
    )
}

@Composable
fun OcrdSelectionDialog(
    ocrds: List< OcrdItem>,
    onAddressSelected: (OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredOcrds = ocrds.filter {
    //    it.Address.lowercase(Locale.getDefault()).contains(searchText.lowercase(Locale.getDefault()), ignoreCase = true)
        val pattern = searchText.split(" ")
            .joinToString(".*") { Pattern.quote(it) }
            .toRegex(RegexOption.IGNORE_CASE)
        pattern.containsMatchIn(it.Address)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text(text = "Buscar punto de venta") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedLabelColor = Color(0xFFCE0303),
                        focusedBorderColor = Color(0xFFCE0303)
                    )
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(filteredOcrds) { ocrd ->
                        Text(
                            text = ocrd.Address,
                            modifier = Modifier
                                .clickable {
                                    marcacionPromotoraViewModel.setIdOcrd(ocrd.id, ocrd.Address)
                                    marcacionPromotoraViewModel.setUbicacionPv(
                                        ocrd.latitud.toDouble(),
                                        ocrd.longitud.toDouble()
                                    )
                                    //marcacionPromotoraViewModel.setMetros(metros)
                                    onAddressSelected(ocrd)
                                }
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFCE0303),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Cerrar")
            }
        }
    )
}


@Composable
fun MyApp(
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
    latitudUsuario: Double,
    longitudUsuario: Double,
) {

    val ocrdList = marcacionPromotoraViewModel.getStoredAddresses()
    val ShowButtonPv by marcacionPromotoraViewModel.showButtonPv.observeAsState(false)
    val ShowButtonSelectPv by marcacionPromotoraViewModel.showButtonSelectPv.observeAsState(true)
    val ButtonPvText by marcacionPromotoraViewModel.buttonPvText.observeAsState(false)
    val ButtonTextRegistro by marcacionPromotoraViewModel.buttonTextRegistro.observeAsState("Iniciar visita")
    val enProceso by marcacionPromotoraViewModel.permitirUbicacion.observeAsState(true)
    val cuadroLoading by marcacionPromotoraViewModel.cuadroLoading.observeAsState(true)
    val cuadroLoadingMensaje by marcacionPromotoraViewModel.cuadroLoadingMensaje.observeAsState("")

    var selectedPV by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    // marcacionPromotoraViewModel.abrirmapaGoogleMaps(context)
    DialogLoading(cuadroLoadingMensaje, cuadroLoading)


    if (showDialog) {
        OcrdSelectionDialog(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            marcacionPromotoraViewModel,

        )
    }
  //  Box(Modifier.fillMaxSize()) {
    Column(
         verticalArrangement = Arrangement.Center,
     ) {
        CuadroMapa(marcacionPromotoraViewModel = marcacionPromotoraViewModel)
        Spacer(modifier = Modifier.height(16.dp))

        if (ShowButtonSelectPv) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(300.dp),
                onClick = {showDialog = true},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFCE0303),
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Seleccionar punto de venta",
                        modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Seleccionar punto de venta",
                        modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                    )
                }
            }
        }
        if (ShowButtonPv) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(300.dp),
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF000000),
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = ButtonPvText.toString(),
                        modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                    )
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(300.dp),
            onClick = {
                //    marcacionPromotoraViewModel.insertarVisita(latitudUsuario, longitudUsuario)
                marcacionPromotoraViewModel.insertarVisita()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (enProceso) Color(0xFFCE0303) else Color(0xFF00641D),
                contentColor = Color.White
            )
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = ButtonTextRegistro,
                    modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                )
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Imagen Visita",
                    modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                )
            }
        }
    }

   // }
}

@Composable
fun CuadroMapa(marcacionPromotoraViewModel: MarcacionPromotoraViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val latitud by marcacionPromotoraViewModel.latitudUsuario.observeAsState(0.0)
    val longitud by marcacionPromotoraViewModel.longitudUsuario.observeAsState(0.0)

    val latitudPV by marcacionPromotoraViewModel.latitudPv.observeAsState(0.0)
    val longitudPV by marcacionPromotoraViewModel.longitudPv.observeAsState(0.0)
    val Pv by marcacionPromotoraViewModel.ocrdName.observeAsState("")

    val markerPosition = LatLng(latitud, longitud)
    val markerPositionPV = LatLng(latitudPV, longitudPV)
    val Paraguay =markerPosition
    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(Paraguay, 17f)}
            GoogleMap(
                modifier = Modifier
                    .height(270.dp) // La composable ocupará el 50% de la pantalla en altura
                    .padding(3.dp), // Agrega un padding opcional
                cameraPositionState = cameraPositionState,
                ) {
                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "MI UBICACIÓN ACTUAL" )
                Marker(
                    state = MarkerState(position = markerPositionPV),
                    title = Pv)
                LaunchedEffect(markerPosition)
                {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(markerPosition))
                 }
                LaunchedEffect(markerPositionPV)
                {
                     cameraPositionState.animate(CameraUpdateFactory.newLatLng(markerPositionPV))
                }
    }
}


private fun prepareBottomMenu(
    context: Context,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel
): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    // add menu items
    bottomMenuItemsList.add( BottomMenuItem(label = "OBTENER PERMISOS",icon = Icons.Filled.PunchClock,{marcacionPromotoraViewModel.obtenerPermisos() }))
    bottomMenuItemsList.add( BottomMenuItem(label = "OBTENER UBICACIÓN",icon = Icons.Filled.TrackChanges,{marcacionPromotoraViewModel.abrirmapaGoogleMaps(context) }))

    return bottomMenuItemsList
}











