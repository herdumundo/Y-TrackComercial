package com.portalgm.y_trackcomercial.ui.visitaAuditor.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PunchClock
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.BottomMenuItem
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogOk
import com.portalgm.y_trackcomercial.components.MyBottomBar
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.portalgm.y_trackcomercial.ui.visitaAuditor.viewmodel.VisitaAuditorViewModel
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.screen.MyApp
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.viewmodel.VisitaSupervisorViewModel
import java.util.regex.Pattern


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VisitaAuditorScreen(
    locationViewModel: LocationLocalViewModel,
    visitaAuditorViewModel: VisitaAuditorViewModel
) {
    val context = LocalContext.current

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        snackbarHost = { SnackbarHost(it) },
        bottomBar = {
            // Agregar un ícono para el menú desplegable
            BottomAppBar(
                backgroundColor = Color(0xFF000000),
                cutoutShape = CircleShape, // Cambiar a CircleShape si quieres un botón flotante
                content = { MyBottomBar( prepareBottomMenu(context,visitaAuditorViewModel))
                })
        },
        content = {
            val latitudUsuario by locationViewModel.latitud.observeAsState()
            val longitudUsuario by locationViewModel.longitud.observeAsState()

            val metros by visitaAuditorViewModel.metros.observeAsState()
            val showDialog by visitaAuditorViewModel.showDialog.observeAsState(false)
            val mensajeDialog by visitaAuditorViewModel.mensajeDialog.observeAsState("")

            LaunchedEffect(Unit) {
                visitaAuditorViewModel.consultaVisitaActiva()
                visitaAuditorViewModel.getAddresses()
                visitaAuditorViewModel.inicializarValores()
                visitaAuditorViewModel.obtenerUbicacion()
            }
            Column {
                // Text(text = "Metros de distancia: ${metros ?: "-"}")

                ScreenVisitaAuditor(visitaAuditorViewModel, latitudUsuario ?: 0.0, longitudUsuario ?: 0.0)
            }
            if (showDialog) {
                InfoDialogOk(
                    title = "Atención",
                    desc = mensajeDialog,
                    image = R.drawable.bolt_uix_no_internet,
                    funcion = { visitaAuditorViewModel.cerrarDialogMensaje() }) {
                }
            }
        }
    )

}

@Composable
fun OcrdSelectionDialog(
    ocrds: List<com.portalgm.y_trackcomercial.data.model.models.OcrdItem>,
    onAddressSelected: (com.portalgm.y_trackcomercial.data.model.models.OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    visitaAuditorViewModel: VisitaAuditorViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredOcrds = ocrds.filter {
     //   it.Address.contains(searchText, ignoreCase = true)
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
                                    visitaAuditorViewModel.setIdOcrd(ocrd.id, ocrd.Address)
                                    visitaAuditorViewModel.setUbicacionPv(
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
fun ScreenVisitaAuditor(
    visitaAuditorViewModel: VisitaAuditorViewModel,
    latitudUsuario: Double,
    longitudUsuario: Double,
) {

    val ocrdList = visitaAuditorViewModel.getStoredAddresses()
    val ShowButtonPv by visitaAuditorViewModel.showButtonPv.observeAsState(false)
    val ShowButtonSelectPv by visitaAuditorViewModel.showButtonSelectPv.observeAsState(true)
    val ButtonPvText by visitaAuditorViewModel.buttonPvText.observeAsState(false)
    val ButtonTextRegistro by visitaAuditorViewModel.buttonTextRegistro.observeAsState("Iniciar visita")
    val enProceso by visitaAuditorViewModel.permitirUbicacion.observeAsState(true)
    var selectedPV by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val ubicacionLoading by visitaAuditorViewModel.ubicacionLoading.observeAsState(true)
    val cuadroLoading by visitaAuditorViewModel.cuadroLoading.observeAsState(true)
    val cuadroLoadingMensaje by visitaAuditorViewModel.cuadroLoadingMensaje.observeAsState("")

    DialogLoading(cuadroLoadingMensaje, cuadroLoading)

    if (showDialog) {
        OcrdSelectionDialog(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            visitaAuditorViewModel,
            //  context
        )
    }

    CuadroMapaAuditor(visitaAuditorViewModel = visitaAuditorViewModel)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ShowButtonSelectPv) {
            Button(
                modifier = Modifier.width(300.dp),
                onClick = { showDialog = true },
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
                        imageVector = Icons.Default.Home,
                        contentDescription = "Seleccionar punto de venta",
                        modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                    )
                }
            }
        }
        if (ShowButtonPv) {
            Button(
                modifier = Modifier.width(300.dp),
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
            modifier = Modifier.width(300.dp),
            onClick = {
             //   visitaAuditorViewModel.insertarVisita(latitudUsuario, longitudUsuario)
                visitaAuditorViewModel.insertarVisita()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if(enProceso)Color(0xFFCE0303)  else Color(0xFF00641D),
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
                    imageVector = Icons.Default.Add,
                    contentDescription = "Imagen Visita",
                    modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                )
            }
        }
    }
}

@Composable
fun CuadroMapaAuditor(visitaAuditorViewModel: VisitaAuditorViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val latitud by visitaAuditorViewModel.latitudUsuario.observeAsState(0.0)
    val longitud by visitaAuditorViewModel.longitudUsuario.observeAsState(0.0)

    val latitudPV by visitaAuditorViewModel.latitudPv.observeAsState(0.0)
    val longitudPV by visitaAuditorViewModel.longitudPv.observeAsState(0.0)
    val Pv by visitaAuditorViewModel.ocrdName.observeAsState("")

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
            title = "MI UBICACIÓN ACTUAL",
        )
        Marker(
            state = MarkerState(position = markerPositionPV),
            title = Pv,
        )
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
    visitaAuditorViewModel: VisitaAuditorViewModel
): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()

    // add menu items
    bottomMenuItemsList.add( BottomMenuItem(label = "OBTENER PERMISOS",icon = Icons.Filled.PunchClock) {visitaAuditorViewModel.obtenerPermisos() })
    bottomMenuItemsList.add( BottomMenuItem(label = "OBTENER UBICACIÓN",icon = Icons.Filled.TrackChanges) {visitaAuditorViewModel.abrirmapaGoogleMaps(
        context
    )
    })
    return bottomMenuItemsList
}