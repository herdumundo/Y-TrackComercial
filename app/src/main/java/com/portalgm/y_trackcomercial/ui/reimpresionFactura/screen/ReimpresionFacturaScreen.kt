package com.portalgm.y_trackcomercial.ui.reimpresionFactura.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import com.portalgm.y_trackcomercial.ui.reimpresionFactura.viewmodel.ReimpresionFacturaViewModel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Print

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.portalgm.y_trackcomercial.components.CardOrdenVenta
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ReimpresionFacturaScreen(
    reimpresionFacturaViewModel: ReimpresionFacturaViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedDateString by reimpresionFacturaViewModel.selectedDate.collectAsState()
    val facturas by reimpresionFacturaViewModel.facturas.collectAsState()
    val cuadroLoading by reimpresionFacturaViewModel.loadingPantalla.observeAsState(false)
    val cuadroLoadingMensaje by reimpresionFacturaViewModel.mensajePantalla.observeAsState("Imprimiendo...")

    DialogLoading(cuadroLoadingMensaje, cuadroLoading)

    // Estado para almacenar la fecha seleccionada

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo de texto para mostrar la fecha seleccionada
        TextField(
            value = selectedDateString,
            onValueChange = {}, // No permitimos cambios directos en el texto
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true, // Hace el campo de texto solo lectura
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Ícono de calendario",
                    modifier = Modifier.clickable {
                        openDatePicker(
                            context,
                            LocalDate.parse(selectedDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            reimpresionFacturaViewModel::setSelectedDate
                        )
                    }

                )
            }
        )

        Button(
            shape = RoundedCornerShape(4.dp),
            onClick = { reimpresionFacturaViewModel.searchFacturasByDate() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
            ) {
            Text( color = Color.White,
                text = "Buscar")
        }

        // Aquí iría el composable para mostrar los detalles de las facturas
        FacturaDetails(facturas,reimpresionFacturaViewModel)
    }
}

@Composable
fun FacturaDetails(
    facturas: List<OinvPosWithDetails>,
    reimpresionFacturaViewModel: ReimpresionFacturaViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(facturas, key = { item -> item.oinvPos.docEntry }) { item ->

            // Text("Factura ID: ${factura.oinvPos.docEntry} - Total: ${factura.oinvPos.address}")
            // Agrega más detalles según necesites
            CardOrdenVenta(
                title1 = item.oinvPos.address!!,
                title2 = item.oinvPos.licTradNum!!,
                title3 = item.oinvPos.numAtCard.toString(),
                buttonText = "Imprimir",
                icono=Icons.Filled.Print,
                onClick = {  reimpresionFacturaViewModel.imprimir(item.oinvPos.docEntry)  }
            )


    }

    }}

fun openDatePicker(
    context: android.content.Context,
    date: LocalDate,
    setDate: (LocalDate) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val newDate = LocalDate.of(year, month + 1, dayOfMonth)
            setDate(newDate)
        },
        date.year,
        date.monthValue - 1,
        date.dayOfMonth
    )
    datePickerDialog.show()
}