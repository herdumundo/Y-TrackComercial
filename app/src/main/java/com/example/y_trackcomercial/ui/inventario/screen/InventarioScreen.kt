package com.example.y_trackcomercial.ui.inventario.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.components.InfoDialog
import com.example.y_trackcomercial.components.SnackAlerta
import com.example.y_trackcomercial.data.model.models.Lotes
import com.example.y_trackcomercial.data.model.models.LotesItem
import com.example.y_trackcomercial.data.model.models.OitmItem
import com.example.y_trackcomercial.data.model.models.UbicacionPv
import com.example.y_trackcomercial.ui.inventario.viewmodel.InventarioViewModel


  @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreenInventario(inventarioViewModel: InventarioViewModel) {
    val snackbarMessage by inventarioViewModel.snackbarMessage.observeAsState()
    val idLote by inventarioViewModel.idLote.observeAsState("")
    val txtCantidad by inventarioViewModel.txtCantidad.observeAsState(initial = "")
    val colorSnack by inventarioViewModel.colorSnack.observeAsState()
    val lotesList = inventarioViewModel.lotesList

     Scaffold(
                 content = {
                 InventarioBody(inventarioViewModel, idLote, txtCantidad,lotesList)
             },
             snackbarHost = {
                 // Show the Snackbar using SnackbarHost
                 if (!snackbarMessage.isNullOrEmpty()) {
                     SnackAlerta(snackbarMessage,Color(colorSnack!!))
                 }
             }
         )
 }


@Composable
fun InventarioBody(
    inventarioViewModel: InventarioViewModel,
    idLote: String,
    txtCantidad: String,
    lotesList: SnapshotStateList<com.example.y_trackcomercial.data.model.models.Lotes>
) {

    val oitmList = inventarioViewModel.getOitm()
    val ubicacionesList = inventarioViewModel.getUbicaciones()
    val LotesList = inventarioViewModel.getLotes()
    var showDialogOitm by remember { mutableStateOf(false) }
    var showDialogUbicacion by remember { mutableStateOf(false) }

    val showDialogLote by inventarioViewModel.showDialogLote.observeAsState(false)
    val showButtonLote by inventarioViewModel.showButtonLote.observeAsState(false)
    val showDialogDelete by inventarioViewModel.showDialogDelete.observeAsState(false)
    val showDialogRegistrar by inventarioViewModel.showDialogRegistrar.observeAsState(false)

    val textButtonLote by inventarioViewModel.textButtonLote.observeAsState("")
    val textButtonProducto by inventarioViewModel.textButtonProducto.observeAsState("")
    val textButtonUbicacion by inventarioViewModel.textButtonUbicacion.observeAsState("")

    LaunchedEffect(Unit) {
        inventarioViewModel.setValoresIniciales()
        inventarioViewModel.setOitm()
        inventarioViewModel.setUbicaciones()
    }

    if (showDialogRegistrar) {
        InfoDialog(title = "Atenciòn!",
            desc = "¿Deseas registrar el inventario?.",
            R.drawable.icono_sincro,
            { inventarioViewModel.registrarInventario() },
            {
                inventarioViewModel.cerrarDialogRegistro()

            })
    }

    if (showDialogDelete) {
        InfoDialog(title = "Atenciòn!",
            desc = "¿Deseas eliminar la fila?.",
            R.drawable.icono_sincro,
            { inventarioViewModel.confirmaRemoverFila() },
            {
                inventarioViewModel.cerrarDialogRemoverFila()

            })
    }

    if (showDialogOitm) {
        OitmSelectionDialog(
            oitms = oitmList,
            onDismiss = {
                showDialogOitm = false
                inventarioViewModel.setShowButtonLote(true)
                // inventarioViewModel.setShowDialogLote(true)
            },
            inventarioViewModel,
        )
    }
    if (showDialogUbicacion) {
        UbicacionSelectionDialog(
            ubicaciones = ubicacionesList,
            onDismiss = {
                showDialogUbicacion = false
            },
            inventarioViewModel,
        )
    }

    if (showDialogLote) {
        LotesSelectionDialog(lotes = LotesList, onLoteSelected = {
            inventarioViewModel.setShowDialogLote(false)
        }, onDismissRequest = {
            // Hacer algo cuando se solicite cerrar el diálogo
        }, inventarioViewModel
        )
    }


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonInventario(
                textButtonUbicacion, Icons.Default.Place, Color(0xFFCE0303)
            ) { showDialogUbicacion = !showDialogUbicacion }

            ButtonInventario(
                      textButtonProducto, Icons.Default.Egg, Color(0xFFCE0303)
            ) { showDialogOitm = !showDialogOitm }



            if (showButtonLote) {
                ButtonInventario(
                    textButtonLote, Icons.Default.Backpack, Color(0xFFCE0303)
                ) { inventarioViewModel.setShowDialogLote(!showDialogLote) }
            }
            if (idLote != "") {
                TextCantidad(txtCantidad) { inventarioViewModel.onCantidadChanged(cantidad = it) }
            }

            if (idLote != "" || txtCantidad != "") {
                ButtonInventario(
                    "Cargar articulo", Icons.Default.AddTask, Color(0xFFFF9800)
                ) { inventarioViewModel.addLotes();  }
            }

            if (idLote.isNullOrBlank() && txtCantidad == "" && lotesList.isNotEmpty()) {
                ButtonInventario(
                    "Registrar", Icons.Default.AppRegistration, Color(0xFFAA0000)
                ) { inventarioViewModel.consultaRegistro()   }
            }

            TableScreen(inventarioViewModel,showDialogDelete)
        }
    }
}
@Composable
fun LotesSelectionDialog(
    lotes: List<com.example.y_trackcomercial.data.model.models.LotesItem>,
    onLoteSelected: () -> Unit,
    onDismissRequest: () -> Unit,
    inventarioViewModel: InventarioViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredLotes = lotes.filter {
        (it.id!!).contains(searchText, ignoreCase = true)
    }
    AlertDialog(onDismissRequest = onDismissRequest, text = {
        Column {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(text = "Buscar Lote") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFCE0303),
                    focusedBorderColor = Color(0xFFCE0303)
                )
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredLotes) { lote ->
                    Text(text = lote.id!!, modifier = Modifier
                        .clickable {
                            inventarioViewModel.setLote(lote.id,lote.CodeBars)
                            onLoteSelected()
                        }
                        .fillMaxWidth()
                        .padding(16.dp))
                }
            }
        }
    }, confirmButton = {
        Button(
            onClick = { onLoteSelected() }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFCE0303), contentColor = Color.White
            )
        ) {
            Text(text = "Cerrar")
        }
    })
}

@Composable
fun OitmSelectionDialog(
    oitms: List<com.example.y_trackcomercial.data.model.models.OitmItem>,
    onDismiss: () -> Unit,
    inventarioViewModel: InventarioViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredOitms = oitms.filter {
        (it.CodeBars!!.takeLast(3) + "" + it.ItemName).contains(searchText, ignoreCase = true)
    }

    AlertDialog(onDismissRequest = onDismiss, text = {
        Column {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(text = "Buscar producto") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFCE0303),
                    focusedBorderColor = Color(0xFFCE0303)
                )
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredOitms) { oitms ->
                    Text(text = oitms.CodeBars!!.takeLast(3) + "-" + oitms.ItemName,
                        modifier = Modifier
                            .clickable {
                                inventarioViewModel.setProducto(
                                    oitms.ItemName!!, oitms.ItemCode
                                )

                                onDismiss()
                            }
                            .fillMaxWidth()
                            .padding(16.dp))
                }
            }
        }
    }, confirmButton = {
        Button(
            onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFCE0303), contentColor = Color.White
            )
        ) {
            Text(text = "Cerrar")
        }
    })

}


@Composable
fun UbicacionSelectionDialog(
    ubicaciones: List<com.example.y_trackcomercial.data.model.models.UbicacionPv>,
    onDismiss: () -> Unit,
    inventarioViewModel: InventarioViewModel,
) {
    var searchText by remember { mutableStateOf("") }

    val filteredubicaciones = ubicaciones.filter {
        it.descripcion!!.contains(searchText, ignoreCase = true)
    }

    AlertDialog(onDismissRequest = onDismiss, text = {
        Column {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text(text = "Buscar ubicaciones") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color(0xFFCE0303),
                    focusedBorderColor = Color(0xFFCE0303)
                )
            )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredubicaciones) { ubicacion ->
                    Text(text = ubicacion.descripcion!!,
                        modifier = Modifier
                            .clickable {
                                inventarioViewModel.setUbicacion(ubicacion.descripcion)
                                onDismiss()
                            }
                            .fillMaxWidth()
                            .padding(16.dp))
                }
            }
        }
    }, confirmButton = {
        Button(
            onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFCE0303), contentColor = Color.White
            )
        ) {
            Text(text = "Cerrar")
        }
    })

}


@Composable
fun TextCantidad(cantidad: String, onTextChanged: (String) -> Unit) {

    val numericOnly = cantidad.filter { it.isDigit() }

    OutlinedTextField(
        value = numericOnly,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        label = { Text("Ingrese cantidad") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color(0xFF770000),
            unfocusedLabelColor = Color(0xFF000000),
            focusedBorderColor = Color(0xFF7A0000),
            unfocusedBorderColor = Color(0xFF000000),
            textColor = Color(0xFF000000)
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}


@Composable
fun ButtonInventario(
    textButton: String, imageVector: ImageVector, color: Color, accionBoton: () -> Unit
) {
    Button(
        modifier = Modifier.width(300.dp),
        onClick = accionBoton,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color, contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = textButton,
                modifier = Modifier.weight(1f) // Ajusta el ancho del Text para llenar el espacio disponible
            )
            Icon(
                imageVector = imageVector,//
                contentDescription = "Seleccionar punto de venta",
                modifier = Modifier.padding(start = 18.dp) // Ajusta el espaciado aquí
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun TableScreen(inventarioViewModel: InventarioViewModel, showDialogDelete: Boolean) {
    val lotesList = inventarioViewModel.lotesList


    val column1Weight = .4f
    val column2Weight = .3f
    val column3Weight = .2f
    val column4Weight = .2f
    val column5Weight = .2f

    LazyColumn(
        Modifier.padding(8.dp)
    ) {
        stickyHeader {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF940000)) // Agrega el color de fondo deseado
                    .padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell(
                    text = "Art.",
                    weight = column1Weight, colorTexto = Color(0xFFFFFFFF),
                    alignment = TextAlign.Left,
                )
                TableCell(
                    text = "Lote.",
                    weight = column2Weight,
                    colorTexto = Color(0xFFFFFFFF),
                    alignment = TextAlign.Left
                )
                TableCell(text = "Cant", weight = column3Weight, colorTexto = Color(0xFFFFFFFF))
                TableCell(text = "Ubic.", weight = column3Weight, colorTexto = Color(0xFFFFFFFF))
                TableCell(
                    text = "Borrar",
                    weight = column4Weight,
                    alignment = TextAlign.Left,
                    colorTexto = Color(0xFFFFFFFF)

                )
            }
            Divider(
                color = Color.LightGray, modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }

        itemsIndexed(lotesList) { index, lotes ->
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TableCell(
                    text = lotes.ItemName,
                    weight = column1Weight,
                    alignment = TextAlign.Left,
                    colorTexto = Color(0xFF000000),

                    )
                TableCell(
                    text = lotes.Lote,
                    weight = column2Weight,
                    colorTexto = Color(0xFF000000),
                    TextAlign.Left
                )
                StatusCell(
                    cantidad = lotes.Cantidad.toString(),
                    weight = column3Weight,
                    TextAlign.Center
                )

                TableCell(
                    text = lotes.ubicacion,
                    weight = column2Weight,
                    colorTexto = Color(0xFF000000),
                    TextAlign.Left
                )
                TableCell(text = "Borrar",
                    weight = column4Weight,
                    alignment = TextAlign.Right,
                    colorTexto = Color(0xFF000000),
                    onClick = { inventarioViewModel.consultaRemoverFilar(index) /*lotesList.removeAt(index)*/ } // Acción de borrar
                )
            }
            Divider(
                color = Color.LightGray, modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    colorTexto: Color,
    alignment: TextAlign = TextAlign.Center,
    onClick: () -> Unit = {}
) {
    if (text != "Borrar") {
        Text(
            text = text,
            Modifier
                .weight(weight)
                .padding(10.dp),
            fontWeight = FontWeight.Bold,
            textAlign = alignment,
            color = colorTexto,
            fontSize = 12.sp,

            )
    } else {
        IconButton(
            onClick = onClick, modifier = Modifier.weight(weight)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = Color(0xFFF70000),
                contentDescription = null
            )
        }
    }
}

@Composable
fun RowScope.StatusCell(
    cantidad: String,
    weight: Float,
    alignment: TextAlign = TextAlign.Center,
) {
//QUIERO HACER DE QUE SI LA CANTIDAD ES MAYOR A 10 Y MENOR A 20 QUE PINTE EN AMARILLO
    // QUE SI ES MAYOR A 20 Y MENOR A 50 QUE PINTE EN NARANJA
    // QUE SI ES MAYOR A 50 Y MENOR A 110 QUE PINTE EN ROJO
    val color = when {
        cantidad.toInt() in 1..19 -> Color(0xFFE9E6E3)
        cantidad.toInt() in 20..40 -> Color(0xFFE0DDAB)
        cantidad.toInt() in 41..70 -> Color(0xFFE79378)
        else -> Color(0xFFF07979)
    }

    val textColor = when {
        cantidad.toInt() in 1..19 -> Color(0xFF000000)
        cantidad.toInt() in 20..40 -> Color(0xFF968C09)
        cantidad.toInt() in 41..70 -> Color(0xFF963A09)
        else -> Color(0xFF4B1111)
    }
    // val textColor = if (color == Color.Yellow) Color.Black else Color.White


    Text(
        text = cantidad,
        Modifier
            .weight(weight)
            .padding(12.dp)
            .background(color, shape = RoundedCornerShape(50.dp)),
        textAlign = alignment,
        color = textColor
    )
}

