package com.example.y_trackcomercial.ui.marcacionPromotora

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.components.InfoDialogOk
import com.example.y_trackcomercial.services.gps.locationLocal.LocationLocalViewModel
import com.example.y_trackcomercial.data.model.models.OcrdItem


@Composable
fun GpsLocationScreen(
    locationViewModel: LocationLocalViewModel,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel
) {
    val latitudUsuario by locationViewModel.latitud.observeAsState()
    val longitudUsuario by locationViewModel.longitud.observeAsState()


    val metros by marcacionPromotoraViewModel.metros.observeAsState()
    val showDialog by marcacionPromotoraViewModel.showDialog.observeAsState(false)
    val mensajeDialog by marcacionPromotoraViewModel.mensajeDialog.observeAsState("")

    LaunchedEffect(Unit) {
        marcacionPromotoraViewModel.consultaVisitaActiva()
        marcacionPromotoraViewModel.getAddresses()
    }
    Column {
       // Text(text = "Metros de distancia: ${metros ?: "-"}")

        MyApp(marcacionPromotoraViewModel, latitudUsuario ?: 0.0, longitudUsuario ?: 0.0)
    }

    if (showDialog) {
        InfoDialogOk(
            title = "Atención",
            desc = mensajeDialog,
            image = R.drawable.bolt_uix_no_internet,
            funcion = { marcacionPromotoraViewModel.cerrarDialogMensaje() }) {

        }

    }
}

@Composable
fun OcrdSelectionDialog(
    ocrds: List<com.example.y_trackcomercial.data.model.models.OcrdItem>,
    /* latitudUsuario: Double,
     longitudUsuario: Double,*/
    onAddressSelected: (com.example.y_trackcomercial.data.model.models.OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    marcacionPromotoraViewModel: MarcacionPromotoraViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredOcrds = ocrds.filter {
        it.Address.contains(searchText, ignoreCase = true)
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


    var selectedPV by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        OcrdSelectionDialog(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            marcacionPromotoraViewModel,
            //  context
        )
    }

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
                        imageVector = Icons.Default.ArrowDropDown,
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
            //    marcacionPromotoraViewModel.insertarVisita(latitudUsuario, longitudUsuario)
                marcacionPromotoraViewModel.insertarVisita()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFCE0303),
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
}

