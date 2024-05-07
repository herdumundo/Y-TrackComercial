package com.portalgm.y_trackcomercial.ui.anulacionFactura.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CancelScheduleSend
import androidx.compose.material.icons.filled.Check
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
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.CardOrdenVenta
import com.portalgm.y_trackcomercial.components.DialogLoading
import com.portalgm.y_trackcomercial.components.InfoDialogDosBoton
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.ui.anulacionFactura.viewmodel.AnulacionFacturaViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AnulacionFacturaScreen(
    anulacionFacturaViewModel: AnulacionFacturaViewModel = viewModel()
) {
    val context = LocalContext.current
    val selectedDateString by anulacionFacturaViewModel.selectedDate.collectAsState()
    val facturas by anulacionFacturaViewModel.facturas.collectAsState()
    val cuadroLoading by anulacionFacturaViewModel.loadingPantalla.observeAsState(false)
    val cuadroLoadingMensaje by anulacionFacturaViewModel.mensajePantalla.observeAsState("Imprimiendo...")
    val pantallaConfirmacion by anulacionFacturaViewModel.pantallaConfirmacion.observeAsState(false)
    val cliente by anulacionFacturaViewModel.cliente.observeAsState("")

    DialogLoading(cuadroLoadingMensaje, cuadroLoading)

    if (pantallaConfirmacion) {
        InfoDialogDosBoton(
            title =cliente,
            titleBottom = "Anular",
            desc = "Desea anular la factura?",
            image = R.drawable.icono_exit,
            funcion = { anulacionFacturaViewModel.anularFactura() })
        {anulacionFacturaViewModel.cancelar()
        }

    }
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
                            LocalDate.parse(
                                selectedDateString,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            ),
                            anulacionFacturaViewModel::setSelectedDate
                        )
                    }

                )
            }
        )

        Button(
            shape = RoundedCornerShape(4.dp),
            onClick = { anulacionFacturaViewModel.searchFacturasByDate() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
        ) {
            Text(
                color = Color.White,
                text = "Buscar"
            )
        }

        // Aquí iría el composable para mostrar los detalles de las facturas
        FacturaDetails(facturas, anulacionFacturaViewModel)
    }
}

@Composable
fun FacturaDetails(
    facturas: List<OinvPosWithDetails>,
    anulacionFacturaViewModel: AnulacionFacturaViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(facturas, key = { item -> item.oinvPos.docEntry }) { item ->

            // Text("Factura ID: ${factura.oinvPos.docEntry} - Total: ${factura.oinvPos.address}")
            // Agrega más detalles según necesites
            CardOrdenVenta(
                title1 = item.oinvPos.address!!,
                title2 = item.oinvPos.licTradNum!!,
                title3 = item.oinvPos.numAtCard.toString(),
                buttonText = if(item.oinvPos.anulado=="N") { "Anular"} else {"Anulado"},
                icono = if(item.oinvPos.anulado=="N") {Icons.Filled.CancelScheduleSend} else {Icons.Filled.Check} ,
                isAnulado = item.oinvPos.anulado != "N",
                isImpresion = false,
                fondoColor =if(item.oinvPos.anulado=="N") { Color(0xFFFFF5A1)
                } else { Color(
                    0xFFA7A4A4
                )
                } ,
                onClick = {if(item.oinvPos.anulado=="N") {  anulacionFacturaViewModel.mensajeConfirmacionCancelacion(item.oinvPos.docEntry,item.oinvPos.address!!)}  }
            )


        }

    }
}

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