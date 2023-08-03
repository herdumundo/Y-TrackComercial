package com.example.y_trackcomercial.ui.exportaciones.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.components.cardViewLoadingTablas
import com.example.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel

@Composable
fun ScreenExportaciones(
    exportacionViewModel: ExportacionViewModel
) {
    LaunchedEffect(Unit) {
        exportacionViewModel.getTablasRegistradas()
        exportacionViewModel.setFalseLoading()


    }
    val visitasCount by exportacionViewModel.visitasCount.observeAsState()
    val auditTrailCount by exportacionViewModel.auditTrailCount.observeAsState()
    val loadingVisitas by exportacionViewModel.loadingVisitas.observeAsState(false)
    val loadingAuditTrail by exportacionViewModel.loadingAuditTrail.observeAsState(false)

    val colorCard = if (visitasCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCard = if (visitasCount == 0) "Visitas sin pendientes" else "Exportar visitas"

    val colorCardAuditTrail = if (auditTrailCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardAuditTrail = if (auditTrailCount == 0) "Auditoria Trail sin pendientes" else "Exportar Auditoria Trail"


    LazyColumn {
        item {
            cardViewLoadingTablas(
                textoLoading = "Enviando visitas...",
                title = mensajeCard,
                color = colorCard,
                subTitle = visitasCount.toString(),
                image = R.drawable.ic_clock_permiso,
                isLoading = loadingVisitas
            ) { exportacionViewModel.enviarPendientes(1) }
        }
        item {
            cardViewLoadingTablas(
                textoLoading = "Enviando Auditoria Trail...",
                title = mensajeCardAuditTrail,
                color = colorCardAuditTrail,
                subTitle = auditTrailCount.toString(),
                image = R.drawable.ic_step,
                isLoading = loadingAuditTrail
            ) { exportacionViewModel.enviarPendientes(2) }
        }
    }
}