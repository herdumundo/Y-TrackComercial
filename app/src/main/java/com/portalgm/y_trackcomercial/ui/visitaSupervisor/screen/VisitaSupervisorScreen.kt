package com.portalgm.y_trackcomercial.ui.visitaSupervisor.screen

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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
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
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.portalgm.y_trackcomercial.ui.marcacionPromotora.MarcacionPromotoraViewModel
import com.portalgm.y_trackcomercial.ui.visitaSupervisor.viewmodel.VisitaSupervisorViewModel
import java.util.regex.Pattern


@Composable
fun VisitaSupervisorScreen(
    locationViewModel: LocationLocalViewModel,
    visitaSupervisorViewModel: VisitaSupervisorViewModel
) {
    val latitudUsuario by locationViewModel.latitud.observeAsState()
    val longitudUsuario by locationViewModel.longitud.observeAsState()


    val metros by visitaSupervisorViewModel.metros.observeAsState()
    val showDialog by visitaSupervisorViewModel.showDialog.observeAsState(false)
    val mensajeDialog by visitaSupervisorViewModel.mensajeDialog.observeAsState("")

    LaunchedEffect(Unit) {
        visitaSupervisorViewModel.consultaVisitaActiva()
        visitaSupervisorViewModel.getAddresses()
        visitaSupervisorViewModel.inicializarValores()
        visitaSupervisorViewModel.obtenerUbicacion()
       // visitaSupervisorViewModel.observarMetrosDistancia()
    }
    Column {
       // Text(text = "Metros de distancia: ${metros ?: "-"}")

        MyApp(visitaSupervisorViewModel, latitudUsuario ?: 0.0, longitudUsuario ?: 0.0)
    }

    if (showDialog) {
        InfoDialogOk(
            title = "Atención",
            desc = mensajeDialog,
            image = R.drawable.bolt_uix_no_internet,
            funcion = { visitaSupervisorViewModel.cerrarDialogMensaje() }) {

        }

    }
}

@Composable
fun OcrdSelectionDialog(
    ocrds: List<OcrdItem>,
    onAddressSelected: (OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    visitaSupervisorViewModel: VisitaSupervisorViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredOcrds = ocrds.filter {
       // it.Address.contains(searchText, ignoreCase = true)
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
                                    visitaSupervisorViewModel.setIdOcrd(ocrd.id, ocrd.Address)
                                    visitaSupervisorViewModel.setUbicacionPv(
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
    visitaSupervisorViewModel: VisitaSupervisorViewModel,
    latitudUsuario: Double,
    longitudUsuario: Double,
) {
    val ocrdList = visitaSupervisorViewModel.getStoredAddresses()
    val ShowButtonPv by visitaSupervisorViewModel.showButtonPv.observeAsState(false)
    val ShowButtonSelectPv by visitaSupervisorViewModel.showButtonSelectPv.observeAsState(true)
    val ButtonPvText by visitaSupervisorViewModel.buttonPvText.observeAsState(false)
    val ButtonTextRegistro by visitaSupervisorViewModel.buttonTextRegistro.observeAsState("Iniciar visita")
    val enProceso by visitaSupervisorViewModel.permitirUbicacion.observeAsState(true)
    val ubicacionLoading by visitaSupervisorViewModel.ubicacionLoading.observeAsState(true)

    DialogLoading("Obteniendo ubicacion actual...", ubicacionLoading)

    var selectedPV by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        OcrdSelectionDialog(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            visitaSupervisorViewModel,
            //  context
        )
    }

    CuadroMapaSupervisor(visitaSupervisorViewModel = visitaSupervisorViewModel)
    Spacer(modifier = Modifier.height(2.dp))

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

                visitaSupervisorViewModel.insertarVisita()
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
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Imagen Visita",
                    modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                )
            }
        }
    }
}


@Composable
fun CuadroMapaSupervisor(visitaSupervisorViewModel: VisitaSupervisorViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val latitud by visitaSupervisorViewModel.latitudUsuario.observeAsState(0.0)
    val longitud by visitaSupervisorViewModel.longitudUsuario.observeAsState(0.0)

    val latitudPV by visitaSupervisorViewModel.latitudPv.observeAsState(0.0)
    val longitudPV by visitaSupervisorViewModel.longitudPv.observeAsState(0.0)
    val Pv by visitaSupervisorViewModel.ocrdName.observeAsState("")

    val markerPosition = LatLng(latitud, longitud)
    val markerPositionPV = LatLng(latitudPV, longitudPV)
    val Paraguay =markerPosition
    var cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(Paraguay, 17f)}
    GoogleMap(
        modifier = Modifier
            .height(360.dp) // La composable ocupará el 50% de la pantalla en altura
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

