package com.portalgm.y_trackcomercial.ui.stockAlmacen.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.ui.stockAlmacen.viewmodel.StockAlmacenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockAlmacenScreen(
    stockAlmacenViewModel: StockAlmacenViewModel,
) {
    LaunchedEffect(Unit) {
        stockAlmacenViewModel.obtenerStockLotes()
        stockAlmacenViewModel.obtenerStockItemCode()
    }
    val stockLotes by stockAlmacenViewModel.stockLotes.observeAsState(emptyList())
    val stockItemCodes by stockAlmacenViewModel.stockItemCodes.observeAsState(emptyList())

    val lotesPorItemCode = remember(stockLotes) {
        stockLotes.groupBy { it.itemCode }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Stock de Almacén", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(stockItemCodes) { item ->
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = { expanded = !expanded }
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF7EB83)) // Fondo amarillo para la cabecera
                                    .padding(16.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = "${item.itemCode}-${item.itemName}", style = MaterialTheme.typography.bodyLarge)
                                 }
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                                    contentDescription = if (expanded) "Show less" else "Show more"
                                )
                            }

                            AnimatedVisibility(visible = expanded) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    lotesPorItemCode[item.itemCode]?.let { lotes ->
                                        lotes.forEach { lote ->
                                            LoteItem(lote)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = (-16).dp, y = (-16).dp)
                            .background(Color.Red)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Total: ${item.quantity}",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

@Composable
fun LoteItem(lote: DatosDetalleLotes) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Lote: ${lote.distNumber}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
        Text(text = "Cantidad: ${lote.quantity}", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
    }
}
