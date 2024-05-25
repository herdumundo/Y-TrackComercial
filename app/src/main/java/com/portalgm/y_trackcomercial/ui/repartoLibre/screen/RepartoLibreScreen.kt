package com.portalgm.y_trackcomercial.ui.repartoLibre.screen

import androidx.compose.runtime.Composable
import com.portalgm.y_trackcomercial.ui.repartoLibre.viewmodel.RepartoLibreViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogDosBotonBoolean
import com.portalgm.y_trackcomercial.components.InfoDialogOk
import com.portalgm.y_trackcomercial.components.cuadroAvisoDenegado
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.ui.repartoLibre.viewmodel.ProductoItem
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun RepartoLibreScreen(
    repartoLibreViewModel: RepartoLibreViewModel,
    navController: NavHostController
) {
    pantallaPrincipal(repartoLibreViewModel =repartoLibreViewModel,navController )

}
@Composable
fun pantallaPrincipal(
    repartoLibreViewModel: RepartoLibreViewModel,
    navController: NavHostController
)
{
    val articulosMenu           by repartoLibreViewModel.productos              .observeAsState(initial = emptyList())
    val lotes                   by repartoLibreViewModel.lotesIncializados      .observeAsState(initial = emptyList())
    val mostrarBotonRegistrar   by repartoLibreViewModel.mostrarBotonRegistrar  .observeAsState( false)
    val registrosConPendiente   by repartoLibreViewModel.registrosConPendiente  .observeAsState(0)
    val cuadroLoadingMensaje    by repartoLibreViewModel.mensajePantalla        .observeAsState("")
    val cuadroLoading           by repartoLibreViewModel.loadingPantalla        .observeAsState(false)
    val dialogPantalla          by repartoLibreViewModel.dialogPantalla         .observeAsState(false)

    DialogLoading(cuadroLoadingMensaje, cuadroLoading)
    if (dialogPantalla) {
        InfoDialogOk(
            title = "Atención",
            desc = cuadroLoadingMensaje,
            image = R.drawable.icono_exit,
            funcion = { navController.navigate("facturacionRepartoLibre") }) {
        }
    }
    if(registrosConPendiente==0){
        cuadroAvisoDenegado("Debes iniciar visita para realizar ventas")
    }
    else{

        LaunchedEffect(true) {
            repartoLibreViewModel.inicializarDatos()
        }
        DisposableEffect(Unit) {
            onDispose {
                repartoLibreViewModel.limpiarLista()
            }
        }
        // Comprobación para ver si la lista está vacía, lo que podría indicar que todavía se están cargando los datos
        if (articulosMenu.isEmpty()) {
            // Mostrar un indicador de carga mientras los datos están siendo cargados
            CircularProgressIndicator()
        } else {
            // Cuando los datos están cargados, muestra la pantalla de menú con los datos.
            MenuScreen(articulosMenu, lotes, repartoLibreViewModel, mostrarBotonRegistrar)
        }
    }
}
@Composable
fun MenuScreen(
    articulosMenu: List<ProductoItem>,
    lotes: List<DatosDetalleLotes>,
    repartoLibreViewModel: RepartoLibreViewModel,
    mostrarBotonRegistrar: Boolean,
) {
    val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
    val formatter = DecimalFormat("#,##0", decimalFormatSymbols)

    val nroFactura by repartoLibreViewModel.nroFactura.observeAsState("")
    val pantallaConfirmacion by repartoLibreViewModel.pantallaConfirmacion.observeAsState(false)
    val pymntGroup by repartoLibreViewModel.pymntGroup.observeAsState("")
    val esContado = pymntGroup == "Contado"
    val datosCliente by repartoLibreViewModel.datosCliente.observeAsState(emptyList())
    val cliente = datosCliente.firstOrNull()


    // Preserva los estados de selección y cantidad a través de recomposiciones
    val selections = remember { mutableStateMapOf<String, MutableState<Boolean>>() }
    val quantities = remember { mutableStateMapOf<String, MutableState<Number>>() }
    val quantitiesUnidad = remember { mutableStateMapOf<String, MutableState<Int>>() }
    val precios = remember { mutableStateMapOf<String, MutableState<Double>>() }
    val itemNames= remember { mutableStateMapOf<String, MutableState<String>>() }
    val existenciaPlancha = remember { mutableStateMapOf<String, Int>() }

    // Inicializa estados si aún no están presentes
    LaunchedEffect(articulosMenu) {
        articulosMenu.forEach { articulo ->
            val existencia = if (articulo.itemCode.length == 1) {
                repartoLibreViewModel.existenciaPlancha(articulo.itemCode )
            } else {
                0
            }
            existenciaPlancha[articulo.itemCode] = existencia

            if (!selections.containsKey(articulo.itemCode)) {
                selections[articulo.itemCode] = mutableStateOf(false)
            }
            if (!quantities.containsKey(articulo.itemCode)) {
                quantities[articulo.itemCode] = mutableStateOf(articulo.initialQuantity)
            }
            if (!quantitiesUnidad.containsKey(articulo.itemCode)) {
                quantitiesUnidad[articulo.itemCode] = mutableStateOf(articulo.quantityUnidad)
            }
            if (!precios.containsKey(articulo.itemCode)) {
                precios[articulo.itemCode] = mutableStateOf(articulo.price)
            }
            if (!itemNames.containsKey(articulo.itemCode)) {
                itemNames[articulo.itemCode] = mutableStateOf(articulo.name)
            }
        }
    }

    val totalCost = articulosMenu.sumOf { articulo ->
        if (selections[articulo.itemCode]?.value == true) {
            val price = articulo.price
            val quantity = quantities[articulo.itemCode]?.value?.toDouble() ?: 0.0
            price * quantity
        } else {
            0.0
        }
    }

    val totalInvoice = formatter.format(totalCost)

    val productosSeleccionados by remember {
        derivedStateOf {
            repartoLibreViewModel.getProductosSeleccionados(
                articulosMenu,
                selections,
                quantities,
                quantitiesUnidad,
                precios,
                itemNames
             )
        }
    }
    if ((pantallaConfirmacion)) {
        InfoDialogDosBotonBoolean(
            title = "Creacion de factura",
            titleBottom = "Procesar",
            desc = "Desea crear el documento?",
            image = R.drawable.icono_exit,
            isActive = mostrarBotonRegistrar,
            funcion = { repartoLibreViewModel.registrar(productosSeleccionados) })
            {
                repartoLibreViewModel.cancelar()
            }
    }
    LaunchedEffect(productosSeleccionados) {
        repartoLibreViewModel.inicializarProductosSeleccionados(productosSeleccionados)
       // Log.d("ProductosSeleccionados", "Productos seleccionados: $productosSeleccionados")
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
                if (cliente != null) {
                    Button(
                        onClick = {
                            repartoLibreViewModel.cambiarTipoPago()
                        },
                        enabled = cliente.GroupNum!!.toInt() > -1 // Solo habilita el botón si GroupNum es mayor que -1
                    ) {
                        Text(text = pymntGroup) //if (esContado) "Cambiar a Crédito" else "Cambiar a Contado")
                    }
                }

            }
            if (mostrarBotonRegistrar) {
                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
                    onClick = {
                         repartoLibreViewModel.mostrarConfirmacion()
                    }
                ) {
                    Text(text = "Registrar", color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(articulosMenu) { articulo ->
                Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio entre los elementos
                Box(modifier = Modifier.fillMaxWidth()) {
                    ProductosItemRow(
                        productosItem = articulo,
                        lotes = lotes,
                        quantity = quantities[articulo.itemCode]?.value ?: 0.0,
                        quantitiesUnidad = quantitiesUnidad[articulo.itemCode]?.value ?: 0,
                        isSelected = selections[articulo.itemCode]?.value == true,
                        repartoLibreViewModel,
                        onQuantityChange = { change ->
                            val currentQuantity = quantities[articulo.itemCode]?.value?.toDouble() ?: 0.0
                            val newQuantity = currentQuantity + change.toDouble()
                            quantities[articulo.itemCode]?.value = newQuantity
                            quantitiesUnidad[articulo.itemCode]?.value = (newQuantity * articulo.baseQty ).toInt()

                            repartoLibreViewModel.comprobarEstadoBoton(productosSeleccionados)
                        },
                        onSelectionChange = { selected ->
                            selections[articulo.itemCode]?.value = selected
                            repartoLibreViewModel.comprobarEstadoBoton(productosSeleccionados)
                        }
                    )
                    if (articulo.unitMsr != "Paquete" && (existenciaPlancha[articulo.itemCode] ?: 0) > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-16).dp, y = (-16).dp)
                                .background(if (articulo.unitMsr == "Plancha") Color.Black else Color.Red)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clickable {
                                    val nuevaUnitMsr = if (articulo.unitMsr == "Plancha") "Docena" else "Plancha"
                                    repartoLibreViewModel.viewModelScope.launch {
                                        val datosCambios=repartoLibreViewModel.cambiarUnitMsr(articulo.itemCode, nuevaUnitMsr)
                                        precios[articulo.itemCode]?.value = datosCambios.price
                                        itemNames[articulo.itemCode]?.value = datosCambios.name
                                        quantities[articulo.itemCode]?.value = 0
                                        quantitiesUnidad[articulo.itemCode]?.value = 0

                                    }
                                }
                        ) {
                            Text(
                                text = if (articulo.unitMsr == "Plancha") "Pasar a Docena" else "Pasar a Plancha",
                                color = Color.White,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp)) // Añadir espacio entre los elementos
            }
        }
    }
}


@Composable
fun ProductosItemRow(
    productosItem: ProductoItem,
    lotes: List<DatosDetalleLotes>,  // La lista completa de lotes
    quantity: Number,
    quantitiesUnidad: Int,
    isSelected: Boolean,
    repartoLibreViewModel: RepartoLibreViewModel,
    onQuantityChange: (Number) -> Unit,
    onSelectionChange: (Boolean) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val cantidadCargada = repartoLibreViewModel.getCantidadCargadaPorItemCode(productosItem.itemCode)

    val textColor =
        if (cantidadCargada.toInt() == 0 && quantitiesUnidad==0 && isSelected) Color(0xFFF5E556)  else if (cantidadCargada.toInt() == quantitiesUnidad && isSelected) Color(0xFFA3FF81) else if (cantidadCargada.toInt() > quantitiesUnidad && isSelected) Color(
            0xFFF86161
        ) else if (isSelected) Color(
            0xFFF5E556
        ) else Color(0xFFB1B1B1)
    var searchText by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = textColor)
    ) {
        Text(
            "Cantidad cargada:${cantidadCargada.toInt()} ",
            style = MaterialTheme.typography.titleSmall
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = onSelectionChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF9E0400),
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White
                    )
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(productosItem.name, style = MaterialTheme.typography.bodySmall)
                    Text(productosItem.itemCode, style = MaterialTheme.typography.bodySmall)

                    if (productosItem.unitMsr != "Paquete") Text(
                        "Unidades: $quantitiesUnidad",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
                    decimalFormatSymbols.groupingSeparator = '.'
                    val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
                    val formattedCost = formatter.format(productosItem.price)
                    Text("Gs. $formattedCost", style = MaterialTheme.typography.bodyLarge)
                    Text(productosItem.unitMsr, style = MaterialTheme.typography.bodyLarge)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val increment = if ( productosItem.unitMsr == "Docena") 0.5 else 1.0
                        IconButton(onClick = { if (quantity.toDouble() > 0) onQuantityChange(-increment) }) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove")
                        }
                        Text(
                            "${if (productosItem.itemCode.length == 1) quantity else quantity.toInt()}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = { if (isSelected) onQuantityChange(increment) }) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text("Buscar Lote") }
                    )
                    val lotesFiltrados = lotes.filter { it.itemCode == productosItem.itemCode }
                        .filter { lote ->
                            searchText.isEmpty() || lote.distNumber?.contains(
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
                                    repartoLibreViewModel.getCantidadIngresadaParaLote(lote.loteLargo!!)
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
                                        Column(modifier = Modifier.weight(2f)) {
                                            Text(
                                                "Lote: ${lote.distNumber}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                "Stock: ${lote.quantity}",
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                        TextField(
                                            value = if(cantidadIngresada.equals("null")) "" else cantidadIngresada,
                                            onValueChange = { newValue ->
                                                newValue.let { nuevaCantidad ->
                                                    if (newValue.toIntOrNull() != null) {
                                                        val nuevaCantidad = newValue.toInt()
                                                        if (nuevaCantidad <= lote.quantity!!.toInt()) {
                                                            cantidadIngresada = nuevaCantidad.toString()
                                                            repartoLibreViewModel.actualizarCantidadIngresada(
                                                                lote.loteLargo!!,
                                                                lote.itemCode!!,
                                                                nuevaCantidad
                                                            )
                                                        }
                                                    } else {
                                                        cantidadIngresada = ""
                                                        repartoLibreViewModel.actualizarCantidadIngresada(
                                                            lote.loteLargo!!,
                                                            lote.itemCode!!,
                                                            0
                                                        )
                                                    }
                                                }
                                            },
                                            singleLine = true,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            label = { Text("Ingrese") },
                                            modifier = Modifier.fillMaxWidth(0.5f)
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
