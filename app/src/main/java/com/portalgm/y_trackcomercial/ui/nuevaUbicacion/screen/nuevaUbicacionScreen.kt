package com.portalgm.y_trackcomercial.ui.nuevaUbicacion.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AltRoute
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.portalgm.y_trackcomercial.components.SnackAlerta
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.ui.nuevaUbicacion.viewmodel.NuevaUbicacionViewModel
import java.util.regex.Pattern


@Composable
fun NuevaUbicacionScreen(nuevaUbicacionViewModel: NuevaUbicacionViewModel) {
    val latitud by nuevaUbicacionViewModel.latitud.observeAsState(0.0)
    val longitud by nuevaUbicacionViewModel.longitud.observeAsState(0.0)
    var showDialog by remember { mutableStateOf(false) }
    val ButtonTextRegistro by nuevaUbicacionViewModel.buttonPvText.observeAsState("Iniciar visita")
    val ButtonUbicacionActual by nuevaUbicacionViewModel.buttonUbicacionActual.observeAsState("Obtener ubicacion actual")
    val idOcrd by nuevaUbicacionViewModel.idOcrd.observeAsState("")
    val registrado by nuevaUbicacionViewModel.registrado.observeAsState(false)

    val ocrdList = nuevaUbicacionViewModel.getStoredAddresses()

    if (showDialog) {
        OcrdSelectionDialogNew(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            nuevaUbicacionViewModel,
            //  context
        )
    }
    LaunchedEffect(Unit)
    {
        nuevaUbicacionViewModel.obtenerUbicacion()
        nuevaUbicacionViewModel.getAddresses()
        nuevaUbicacionViewModel.inicializarValores()
    }

    val markerPosition = LatLng(latitud, longitud)

    val Paraguay =markerPosition
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Paraguay, 10f)}

    Box(Modifier.fillMaxSize()) {
        Column {
            GoogleMap(
                modifier = Modifier
                    .height(350.dp) // La composable ocupará el 50% de la pantalla en altura
                    .padding(3.dp), // Agrega un padding opcional
                cameraPositionState = cameraPositionState,

                ) {
                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "Ubicación Actual",
                    snippet = "1"
                )
                LaunchedEffect(markerPosition)
                {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(markerPosition))
                }
            }
           // Spacer(modifier = Modifier.height(16.dp)) // Add space between the map and button
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally).width(300.dp),
                onClick = { nuevaUbicacionViewModel.obtenerUbicacion()},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFAA0000),
                    contentColor = Color.White
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = ButtonUbicacionActual!!,
                        modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                    )
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                    )
                }
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally).width(300.dp),
                onClick = { showDialog = true },
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
                        text = ButtonTextRegistro,//"Seleccionar punto de venta",
                        modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Seleccionar punto de venta",
                        modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if(idOcrd!=""){

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally).width(300.dp),
                    onClick = {nuevaUbicacionViewModel.registrar() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF003108),
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Actualizar ubicacion del punto de venta",
                            modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
                        )
                        Icon(
                            imageVector = Icons.Default.AltRoute,
                            contentDescription = "-",
                            modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
                        )
                    }
                }
            }
            if(registrado){
                SnackAlerta("Registrado con exito",Color(0xFF161010))
                Spacer(modifier = Modifier.height(25.dp))
            }
        }
    }


}


@Composable
fun OcrdSelectionDialogNew(
    ocrds: List<OcrdItem>,
    onAddressSelected: (OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    nuevaUbicacionViewModel: NuevaUbicacionViewModel,
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
                                    nuevaUbicacionViewModel.setIdOcrd(ocrd.id, ocrd.Address)
                                    /*nuevaUbicacionViewModel.setUbicacionPv(
                                        ocrd.latitud.toDouble(),
                                        ocrd.longitud.toDouble()
                                    )*/
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
