package com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.viewmodel.OrdenVentaDetalleViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogDosBoton
import com.portalgm.y_trackcomercial.components.InfoDialogDosBotonBoolean
import com.portalgm.y_trackcomercial.components.InfoDialogOk
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrdenVentaDetalleScreen(
    ordenVentaDetalleViewModel: OrdenVentaDetalleViewModel,
    docNum: Int,
    navController: NavHostController
) {
    val articulosMenu by ordenVentaDetalleViewModel.productos.observeAsState(initial = emptyList())
    val lotes by ordenVentaDetalleViewModel.lotesIncializados.observeAsState(initial = emptyList())
    val mostrarBotonRegistrar by ordenVentaDetalleViewModel.mostrarBotonRegistrar.observeAsState(false)
    val dialogPantalla by ordenVentaDetalleViewModel.dialogPantalla.observeAsState(false)
    val cuadroLoading by ordenVentaDetalleViewModel.loadingPantalla.observeAsState(false)
    val cuadroLoadingMensaje by ordenVentaDetalleViewModel.mensajePantalla.observeAsState("")

    DialogLoading(cuadroLoadingMensaje, cuadroLoading)
    if (dialogPantalla) {
        InfoDialogOk(
            title = "Atención",
            desc = cuadroLoadingMensaje,
            image = R.drawable.icono_exit,
            funcion = { navController.navigate("ordenVenta") }) {
        }
    }
    LaunchedEffect(true) {
        ordenVentaDetalleViewModel.inicializarDatos(docNum)
    }
    DisposableEffect(Unit) {
        onDispose {
            ordenVentaDetalleViewModel.limpiarLista()
        }
    }
    // Comprobación para ver si la lista está vacía, lo que podría indicar que todavía se están cargando los datos
    if (articulosMenu.isEmpty()) {
        // Mostrar un indicador de carga mientras los datos están siendo cargados
        CircularProgressIndicator()
    } else {
        // Cuando los datos están cargados, muestra la pantalla de menú con los datos.
        MenuScreen(articulosMenu, lotes, ordenVentaDetalleViewModel,mostrarBotonRegistrar)
    }
}

@Composable
fun MenuScreen(
    articulosMenu: List<ProductoItem>,
    lotes: List<DatosDetalleLotes>,
    ordenVentaDetalleViewModel: OrdenVentaDetalleViewModel,
    mostrarBotonRegistrar: Boolean,
) {
    val nroFactura              by ordenVentaDetalleViewModel.nroFactura.observeAsState("")
    val pantallaConfirmacion    by ordenVentaDetalleViewModel.pantallaConfirmacion.observeAsState(false)

    // Estados de cantidades y selección
    val quantities = remember { articulosMenu.associateWith { mutableStateOf(it.initialQuantity) } }
    val selections = remember { articulosMenu.associateWith { mutableStateOf(true) } }
    // Cálculo del costo total basado en los ítems seleccionados
    val totalCost = articulosMenu.sumOf { articulo ->
        if (selections[articulo]?.value == true) articulo.price * (quantities[articulo]?.value
            ?: 0) else 0
    }
    val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
    val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
    val totalInvoice = formatter.format(totalCost)

    // Obtener los productos seleccionados
    val productosSeleccionados by remember {
        derivedStateOf {
            ordenVentaDetalleViewModel.getProductosSeleccionados(articulosMenu, selections, quantities)
        }
    }

    if ((pantallaConfirmacion)) {
        InfoDialogDosBotonBoolean(
            title ="Creacion de factura",
            titleBottom = "Procesar",
            desc = "Desea crear el documento?",
            image = R.drawable.icono_exit,
            isActive = mostrarBotonRegistrar,
            funcion = {  ordenVentaDetalleViewModel.registrar(productosSeleccionados) })
        {ordenVentaDetalleViewModel.cancelar()
        }
    }
    // Llamada inicial para calcular y comprobar el estado del botón
    LaunchedEffect(productosSeleccionados) {
        ordenVentaDetalleViewModel.inicializarProductosSeleccionados(productosSeleccionados)
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Total: Gs. $totalInvoice", style = MaterialTheme.typography.headlineSmall)
                Text("Nro. Factura: $nroFactura ", style = MaterialTheme.typography.headlineSmall)
            }

           if(mostrarBotonRegistrar){
               Button(
                modifier = Modifier,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
                onClick = {
                    ordenVentaDetalleViewModel.mostrarConfirmacion()
                 }
            ) {
                Text(text = "Registrar", color = Color.White)
            }}
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articulosMenu) { articulo ->
                    ProductosItemRow(
                        productosItem = articulo,
                        lotes = lotes,
                        quantity = quantities[articulo]?.value ?: 0,
                        isSelected = selections[articulo]?.value == true,
                        ordenVentaDetalleViewModel,
                        onQuantityChange = { change ->
                            quantities[articulo]?.value =
                                (quantities[articulo]?.value ?: 0) + change
                            ordenVentaDetalleViewModel.comprobarEstadoBoton(productosSeleccionados)
                        },
                        onSelectionChange = { selected ->
                            selections[articulo]?.value = selected
                            ordenVentaDetalleViewModel.comprobarEstadoBoton(productosSeleccionados)
                        }
                    )
                }
         }
    }
}


@Composable
fun ProductosItemRow(
    productosItem: ProductoItem,
    lotes: List<DatosDetalleLotes>,  // La lista completa de lotes
    quantity: Int,
    isSelected: Boolean,
    ordenVentaDetalleViewModel: OrdenVentaDetalleViewModel,
    onQuantityChange: (Int) -> Unit,
    onSelectionChange: (Boolean) -> Unit,
    ) {
    var expanded by remember { mutableStateOf(false) }
    val cantidadCargada = ordenVentaDetalleViewModel.getCantidadCargadaPorItemCode(productosItem.itemCode)
    val textColor = if (cantidadCargada == quantity && isSelected) Color(0xFFA3FF81) else if(cantidadCargada > quantity && isSelected) Color(0xFFFF3F3F)else if(isSelected) Color(
        0xFFF5E556
    ) else Color(0xFFFFFFFF)
    var searchText by remember { mutableStateOf("") } // Definir searchText aquí

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = textColor) // Usa el color que prefieras aquí
    ) {
        Text("Cantidad cargada:$cantidadCargada ", style = MaterialTheme.typography.bodyMedium)
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { expanded = !expanded }, // Permite expandir o colapsar el acordeón
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectionChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF9E0400),  // Color cuando el Checkbox está seleccionado
                        uncheckedColor = Color.Black,  // Color cuando el Checkbox no está seleccionado
                        checkmarkColor = Color.White // Color de la marca de verificación
                    )
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(productosItem.name, style = MaterialTheme.typography.bodySmall)
                    Text(productosItem.itemCode, style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
                    decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
                    val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
                    val formattedCost = formatter.format(productosItem.price)
                    Text("Gs. $formattedCost", style = MaterialTheme.typography.bodyLarge)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { if (quantity > 0) onQuantityChange(-1) }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove")
                        }
                        Text("$quantity", style = MaterialTheme.typography.bodyLarge)
                        IconButton(onClick = { if (isSelected) onQuantityChange(1) }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }
            // Contenido expandible del acordeón
            AnimatedVisibility(visible = expanded) {
              //  val lotesFiltrados = lotes.filter { it.itemCode == productosItem.itemCode }
                Column {
                    // Agregar un TextField para buscar lotes
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Buscar Lote") }
                    )
                    // Filtrar los lotes basándose en el texto de búsqueda
                    val lotesFiltrados = lotes.filter { it.itemCode == productosItem.itemCode }
                        .filter { lote ->
                            searchText.isEmpty() || lote.batchNum?.contains(
                                searchText,
                                ignoreCase = true
                            ) == true
                        }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                ) {
                    items(lotesFiltrados) { lote ->
                         var cantidadIngresada by rememberSaveable {
                            mutableStateOf(
                                ordenVentaDetalleViewModel.getCantidadIngresadaParaLote(lote.batchNum!!)
                            )
                        }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Column(modifier = Modifier.weight(2f)) { // Aumenta el peso si necesitas más espacio para los textos
                                        Text(
                                            "Lote: ${lote.batchNum}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            "Stock: ${lote.quantity}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    // Ajusta el tamaño del TextField, por ejemplo usando fillMaxWidth con un factor menor
                                    TextField(
                                        value = cantidadIngresada,
                                        onValueChange = { newValue ->

                                            newValue.let { nuevaCantidad ->
                                                if (newValue.toIntOrNull() != null) {
                                                    val nuevaCantidad = newValue.toInt()
                                                    if (nuevaCantidad <= lote.quantity!!.toInt()) {
                                                        cantidadIngresada = nuevaCantidad.toString()
                                                        ordenVentaDetalleViewModel.actualizarCantidadIngresada(lote.batchNum!!, lote.itemCode!!, nuevaCantidad)
                                                    }
                                                } else {
                                                    // Si newValue no es un número, dejar el campo vacío
                                                    cantidadIngresada = ""
                                                    ordenVentaDetalleViewModel.actualizarCantidadIngresada(lote.batchNum!!, lote.itemCode!!, 0)
                                                }

                                            }
                                        },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        label = { Text("Ingrese") },
                                        modifier = Modifier.fillMaxWidth(0.5f) // Usa un factor como 0.5 para reducir el ancho del TextField
                                    )
                                }
                            }


                        }
                    }
                }
            }
            }
        }
    }
    Divider()
}


data class ProductoItem(
    val lineNum: Int,
    val name: String,
    val itemCode: String,
    val price: Int,
    val initialQuantity: Int
)

