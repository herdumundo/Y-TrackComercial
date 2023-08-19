package com.ytrack.y_trackcomercial.ui.visitaHorasTranscurridas.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ytrack.y_trackcomercial.components.AccordionGroup
import com.ytrack.y_trackcomercial.components.AccordionModel
import com.ytrack.y_trackcomercial.data.model.models.HorasTranscurridasPv
import com.ytrack.y_trackcomercial.ui.visitaHorasTranscurridas.viewmodel.VisitasHorasTranscurridasViewModel

@Composable
fun VisitasHorasTranscurridasScreen(viewModel: VisitasHorasTranscurridasViewModel) {
    val listaHorasTranscurridas: List<HorasTranscurridasPv> by viewModel.listaHorasTranscurridas.observeAsState(
        emptyList()
    )

    LaunchedEffect(viewModel) {
        viewModel.obtenerLista()
    }
    val group = listaHorasTranscurridas.map { item ->
        AccordionModel(
            header = item.OcrdName ?: "",
            rows = mutableListOf(
                AccordionModel.Row(title = "Hora de entrada", item.inicio ?: ""),
                AccordionModel.Row(title = "Hora de Salida", item.fin ?: ""),
                AccordionModel.Row(title = "Horas transcurridas", item.minutos_transcurridos ?: ""),
            )
        )
    }
    Content(group)
}

@Composable
fun Content(group: List<AccordionModel>) {

    Column {
        AccordionGroup(
            modifier = Modifier.padding(top = 8.dp),
            group = group
        )
    }
}