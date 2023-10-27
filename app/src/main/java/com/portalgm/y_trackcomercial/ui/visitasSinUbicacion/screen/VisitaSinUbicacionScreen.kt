package com.portalgm.y_trackcomercial.ui.visitasSinUbicacion.screen

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
import androidx.compose.material.icons.filled.Add
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
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogOk
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.ui.visitasSinUbicacion.viewmodel.VisitaSinUbicacionViewModel
import java.util.regex.Pattern

@Composable
fun VisitaSinUbicacionScreen(
    visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel
) {

    val showDialog by visitaSinUbicacionViewModel.showDialog.observeAsState(false)
    val mensajeDialog by visitaSinUbicacionViewModel.mensajeDialog.observeAsState("")

    LaunchedEffect(Unit) {
        visitaSinUbicacionViewModel.consultaVisitaActiva()
        visitaSinUbicacionViewModel.getAddresses()
        visitaSinUbicacionViewModel.inicializarValores()

    }
    Column {
       // Text(text = "Metros de distancia: ${metros ?: "-"}")

        ScreenVisitaSinUbicacion(visitaSinUbicacionViewModel)
    }

    if (showDialog) {
        InfoDialogOk(
            title = "Atención",
            desc = mensajeDialog,
            image = R.drawable.bolt_uix_no_internet,
            funcion = { visitaSinUbicacionViewModel.cerrarDialogMensaje() }) {

        }

    }
}

@Composable
fun OcrdSelectionDialog(
    ocrds: List<OcrdItem>,
    onAddressSelected: (OcrdItem) -> Unit,
    onDismissRequest: () -> Unit,
    visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel,
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
                                    visitaSinUbicacionViewModel.setIdOcrd(ocrd.id, ocrd.Address)
                                    visitaSinUbicacionViewModel.setUbicacionPv(
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
fun ScreenVisitaSinUbicacion(
    visitaSinUbicacionViewModel: VisitaSinUbicacionViewModel
) {

    val ocrdList = visitaSinUbicacionViewModel.getStoredAddresses()
    val ShowButtonPv by visitaSinUbicacionViewModel.showButtonPv.observeAsState(false)
    val ShowButtonSelectPv by visitaSinUbicacionViewModel.showButtonSelectPv.observeAsState(true)
    val ButtonPvText by visitaSinUbicacionViewModel.buttonPvText.observeAsState(false)
    val ButtonTextRegistro by visitaSinUbicacionViewModel.buttonTextRegistro.observeAsState("Iniciar visita")
    val enProceso by visitaSinUbicacionViewModel.permitirUbicacion.observeAsState(true)
    var selectedPV by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    if (showDialog) {
        OcrdSelectionDialog(
            ocrds = ocrdList,
            onAddressSelected = { ocrd ->
                showDialog = false
            },
            onDismissRequest = { showDialog = false },
            visitaSinUbicacionViewModel,
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
                visitaSinUbicacionViewModel.insertarVisita()
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

