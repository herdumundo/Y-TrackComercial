package com.portalgm.y_trackcomercial.ui.stockAlmacen.screen

import androidx.compose.foundation.layout.*
 import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.portalgm.y_trackcomercial.components.CardOrdenVenta2
import com.portalgm.y_trackcomercial.ui.stockAlmacen.viewmodel.StockAlmacenViewModel

@Composable
fun StockAlmacenScreen(
    stockAlmacenViewModel: StockAlmacenViewModel,
) {
    LaunchedEffect(Unit) {
        stockAlmacenViewModel.obtenerStockLotes()
    }

    val stockLotes by stockAlmacenViewModel.stockLotes.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Stock de Almacén", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(stockLotes) { item ->
                CardOrdenVenta2(
                    title1 = item.itemName!!,
                    title2 = item.distNumber!!,
                    title3 = item.itemCode!!,
                    buttonText=item.quantity.toString(),
                    icono= Icons.Filled.Egg,
                    isAnulado = false,
                    isImpresion = false,
                    fondoColor = Color(0xFFD6D2CE),
                    onClick = { }
                )
            }
        }
    }
}

