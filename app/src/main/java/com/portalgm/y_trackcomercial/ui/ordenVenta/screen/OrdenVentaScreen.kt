package com.portalgm.y_trackcomercial.ui.ordenVenta.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.portalgm.y_trackcomercial.components.CardOrdenVenta
import com.portalgm.y_trackcomercial.components.cuadroAvisoDenegado
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.ui.ordenVenta.viewmodel.OrdenVentaViewModel

@Composable
fun OrdenVentaScreen(
    ordenVentaViewModel: OrdenVentaViewModel,
     navController: NavHostController
) {
    val listaOrdenVenta: List<OrdenVentaCabItem> by ordenVentaViewModel.listaOrdenVenta.observeAsState(initial = emptyList())
    val isLoading by ordenVentaViewModel.isLoading.observeAsState(initial = false)
    val registrosConPendiente by ordenVentaViewModel.registrosConPendiente.observeAsState(0)

    LaunchedEffect(ordenVentaViewModel) {
        ordenVentaViewModel.obtenerLista()
    }
    if(registrosConPendiente==0){
        cuadroAvisoDenegado("Debes iniciar visita para realizar ventas")
    }
    else{
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listaOrdenVenta, key = { item -> item.docNum }) { item ->
                    CardOrdenVenta(
                        title1 = item.shipToCode,
                        title2 = "Fecha de entrega: "+  item.docDueDate ,
                        title3 = item.docNum,
                        buttonText="Ir",
                        icono= Icons.Filled.ReceiptLong,
                        isAnulado = false,
                        isImpresion = false,
                        fondoColor = Color(0xFFFFF5A1),
                        onClick = {  navController.navigate("ordenVentaDetalle/${item.docNum}") }
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

}
