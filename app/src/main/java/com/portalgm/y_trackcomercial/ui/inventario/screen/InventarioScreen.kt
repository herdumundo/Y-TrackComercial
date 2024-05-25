package com.portalgm.y_trackcomercial.ui.inventario.screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomAppBar
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
import androidx.compose.material.icons.filled.PunchClock
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portalgm.y_trackcomercial.components.InfoDialog
import com.portalgm.y_trackcomercial.components.SnackAlerta
import com.portalgm.y_trackcomercial.ui.inventario.viewmodel.InventarioViewModel
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.BottomMenuItem
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.MyBottomBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import com.portalgm.y_trackcomercial.components.cuadroAvisoDenegado
import com.portalgm.y_trackcomercial.data.model.models.Lotes
import com.portalgm.y_trackcomercial.data.model.models.LotesItem
import com.portalgm.y_trackcomercial.data.model.models.UbicacionPv

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ScreenInventario(inventarioViewModel: InventarioViewModel) {

    val registrosConPendiente by inventarioViewModel.registrosConPendiente.observeAsState(0)

    if(registrosConPendiente==0){
        cuadroAvisoDenegado("Debes iniciar visita para realizar el inventario")
    }
    else{
        val snackbarMessage by inventarioViewModel.snackbarMessage.observeAsState()
        val idLote by inventarioViewModel.idLote.observeAsState("")
        val txtCantidad by inventarioViewModel.txtCantidad.observeAsState(initial = "")
        val colorSnack by inventarioViewModel.colorSnack.observeAsState()
        val lotesList = inventarioViewModel.lotesList
        val cuadroLoading by inventarioViewModel.cuadroLoading.observeAsState(false)
        val cuadroLoadingMensaje by inventarioViewModel.cuadroLoadingMensaje.observeAsState("")

        DialogLoading(cuadroLoadingMensaje, cuadroLoading)
        Scaffold(
            content = {
                InventarioBody(inventarioViewModel, idLote, txtCantidad,lotesList)
            },
            snackbarHost = {
                // Show the Snackbar using SnackbarHost
                if (!snackbarMessage.isNullOrEmpty()) {
                    SnackAlerta(snackbarMessage,Color(colorSnack!!))
                }
            },

            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .absolutePadding(right = 330.dp, bottom = 1.dp) // Ajusta la posición a la izquierda
                ) {
                    FloatingActionButton(
                        onClick = {
                            inventarioViewModel.obtenerLotesNuevos()
                        },
                        modifier = Modifier.size(56.dp),
                        backgroundColor = Color.Black // Color de fondo negro
                    ) {
                        Icon(imageVector = Icons.Default.PunchClock, contentDescription = "Agregar",tint=Color.White)
                    }
                }
            }

        )

    }

 }


@Composable
fun InventarioBody(
    inventarioViewModel: InventarioViewModel,
    idLote: String,
    txtCantidad: String,
    lotesList: SnapshotStateList<Lotes>
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
    val itemName = inventarioViewModel.itemName
    val itemCode = inventarioViewModel.itemCode
    val codeBar = inventarioViewModel.codeBar

    LaunchedEffect(Unit) {
        inventarioViewModel.setValoresIniciales()
        inventarioViewModel.setOitm()
        inventarioViewModel.setUbicaciones()
       // inventarioViewModel.obtenerLotesNuevos()
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
        }, inventarioViewModel,itemName.value!!,itemCode.value!!
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
    lotes: List<LotesItem>,
    onLoteSelected: () -> Unit,
    onDismissRequest: () -> Unit,
    inventarioViewModel: InventarioViewModel,
    itemName: String,
    itemCode: String
 ) {
    var searchText by remember { mutableStateOf("") }

    val filteredLotes = lotes.filter {
        (it.id!!).contains(searchText, ignoreCase = true)
    }
    AlertDialog(onDismissRequest = onDismissRequest, text = {
        Column {
            Text(text = "$itemCode-$itemName",    fontWeight = FontWeight.Bold
            )
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
                            inventarioViewModel.setLote(lote.id, lote.CodeBars)
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
    oitms: List<com.portalgm.y_trackcomercial.data.model.models.OitmItem>,
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
                ) )
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(filteredOitms) { oitms ->
                    Text(text = oitms.ItemCode +"-"+oitms.CodeBars!!.takeLast(3) + "-" + oitms.ItemName,
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
    ubicaciones: List<UbicacionPv>,
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


private fun prepareBottomMenuInventario(
     inventarioViewModel: InventarioViewModel
): List<BottomMenuItem> {
    val bottomMenuItemsList = arrayListOf<BottomMenuItem>()
    // add menu items
    bottomMenuItemsList.add(
        BottomMenuItem(
            label = "OBTENER NUEVOS LOTES",
            icon = Icons.Filled.PunchClock
        ) { inventarioViewModel.obtenerLotesNuevos() }
    )
    return bottomMenuItemsList
}






@Composable
fun AreShapeOnBorderCenterSurface(
    cornerRadius: Dp,
    centerCircleRadius: Dp,
    content: @Composable () -> Unit
) {

    val density = LocalDensity.current
    val cornerRadiusPx = density.run {
//        15.dp.toPx()
        cornerRadius.toPx()
    }
    val centerCircleRadiusPx = density.run {
//        50.dp.toPx()
        centerCircleRadius.toPx()
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .wrapContentHeight(),
        elevation = 5.dp,
        color = Color.White,
        shape = GenericShape { size: androidx.compose.ui.geometry.Size, _: androidx.compose.ui.unit.LayoutDirection ->
            buildCustomPath(size, cornerRadiusPx, centerCircleRadiusPx)
        },
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.6f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = centerCircleRadius)
        ) {
            content()
        }
    }
}

fun Path.buildCustomPath(size: androidx.compose.ui.geometry.Size, cornerRadius: Float, centerCircleRadius: Float) {
    val width = size.width
    val height = size.height

    // 顶部简化计算的
    val topHalfMoveLength = (width - 2 * cornerRadius - 2 * centerCircleRadius) / 2

    // 单位长度
    val smallCubeLength = centerCircleRadius / 20

    val firstCubicPoint1 = Offset(
        x = 1 * cornerRadius + topHalfMoveLength + 8 * smallCubeLength,
        y = 1 * smallCubeLength
    )
    val firstCubicPoint2 = Offset(
        x = 1 * cornerRadius + topHalfMoveLength + 4 * smallCubeLength,
        y = 16 * smallCubeLength
    )
    val firstCubicTarget = Offset(
        x = 1 * cornerRadius + topHalfMoveLength + centerCircleRadius,
        y = 16 * smallCubeLength
    )
    val secondCubicPoint1 = Offset(
        x = width - firstCubicPoint2.x,
        y = firstCubicPoint2.y
    )
    val secondCubicPoint2 = Offset(
        x = width - firstCubicPoint1.x,
        y = firstCubicPoint1.y
    )
    val secondCubicTarget = Offset(
        x = 1 * cornerRadius + topHalfMoveLength + 2 * centerCircleRadius,
        y = 0f
    )


    moveTo(cornerRadius, 0f)
    lineTo(cornerRadius + topHalfMoveLength, 0f)

    cubicTo(
        x1 = firstCubicPoint1.x,
        y1 = firstCubicPoint1.y,
        x2 = firstCubicPoint2.x,
        y2 = firstCubicPoint2.y,
        x3 = firstCubicTarget.x,
        y3 = firstCubicTarget.y,
    )
    cubicTo(
        x1 = secondCubicPoint1.x,
        y1 = secondCubicPoint1.y,
        x2 = secondCubicPoint2.x,
        y2 = secondCubicPoint2.y,
        x3 = secondCubicTarget.x,
        y3 = secondCubicTarget.y,
    )

    lineTo(width - cornerRadius, 0f)
    arcTo(
        rect = Rect(
            topLeft = Offset(x = width - 2 * cornerRadius, y = 0f),
            bottomRight = Offset(x = width, y = 2 * cornerRadius)
        ),
        startAngleDegrees = -90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(width, height - cornerRadius)
    arcTo(
        rect = Rect(
            topLeft = Offset(x = width - 2 * cornerRadius, y = height - 2 * cornerRadius),
            bottomRight = Offset(x = width, y = height)
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(0f + cornerRadius, height)
    arcTo(
        rect = Rect(
            topLeft = Offset(x = 0f, y = height - 2 * cornerRadius),
            bottomRight = Offset(x = 2 * cornerRadius, y = height)
        ),
        startAngleDegrees = 90f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    lineTo(0f, cornerRadius)
    arcTo(
        rect = Rect(
            topLeft = Offset.Zero,
            bottomRight = Offset(x = 2 * cornerRadius, y = 2 * cornerRadius)
        ),
        startAngleDegrees = 180f,
        sweepAngleDegrees = 90f,
        forceMoveTo = false
    )
    close()
}


