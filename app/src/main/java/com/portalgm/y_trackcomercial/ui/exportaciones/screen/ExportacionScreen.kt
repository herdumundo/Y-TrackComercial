package com.portalgm.y_trackcomercial.ui.exportaciones.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.cardViewLoadingTablas
import com.portalgm.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel

@Composable
fun ScreenExportaciones(
    exportacionViewModel: ExportacionViewModel
) {
    LaunchedEffect(Unit) {
        exportacionViewModel.getTablasRegistradasTotal()
        exportacionViewModel.setFalseLoading()
    }

    val visitasCount by exportacionViewModel.visitasCount.observeAsState()
    val auditTrailCount by exportacionViewModel.auditTrailCount.observeAsState()
    val logCount by exportacionViewModel.logCount.observeAsState()
    val movimientosCount by exportacionViewModel.movimientosCount.observeAsState()
    val ubicacionesNuevasCount by exportacionViewModel.ubicacionesNuevasCount.observeAsState()

    val loadingVisitas by exportacionViewModel.loadingVisitas.observeAsState(false)
    val loadingAuditTrail by exportacionViewModel.loadingAuditTrail.observeAsState(false)
    val loadingLog by exportacionViewModel.loadingLog.observeAsState(false)
    val loadingMovimientos by exportacionViewModel.loadingMovimientos.observeAsState(false)
    val loadingNuevasUbicaciones by exportacionViewModel.loadingNuevasUbicaciones.observeAsState(false)

    val colorCard = if (visitasCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCard = if (visitasCount == 0) "Visitas sin pendientes" else "Exportar visitas"

    val colorCardAuditTrail = if (auditTrailCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardAuditTrail = if (auditTrailCount == 0) "Auditoria Trail sin pendientes" else "Exportar Auditoria Trail"

    val colorCardLog = if (logCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardLog = if (logCount == 0) "Log de actividades sin pendientes" else "Exportar Log de actividades"

    val colorCardMovimientos = if (movimientosCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardMovimientos = if (movimientosCount == 0) "Movimientos sin pendientes" else "Exportar Movimientos"


    val colorCardNuevasUbicaciones = if (ubicacionesNuevasCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardNuevasUbicaciones= if (ubicacionesNuevasCount == 0) "Ubicaciones nuevas sin pendientes" else "Exportar Ubicaciones Nuevas"



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
        item {
            cardViewLoadingTablas(
                textoLoading = "Enviando Log de actividades...",
                title = mensajeCardLog,
                color = colorCardLog,
                subTitle = logCount.toString(),
                image = R.drawable.ic_log_activity,
                isLoading = loadingLog
            ) { exportacionViewModel.enviarPendientes(3) }
        }
        item {
            cardViewLoadingTablas(
                textoLoading = "Enviando Movimientos...",
                title = mensajeCardMovimientos,
                color = colorCardMovimientos,
                subTitle = movimientosCount.toString(),
                image = R.drawable.ic_moving,
                isLoading = loadingMovimientos
            ) { exportacionViewModel.enviarPendientes(4) }
        }

        item {
            cardViewLoadingTablas(
                textoLoading = "Enviando Nuevas Ubicaciones...",
                title = mensajeCardNuevasUbicaciones,
                color = colorCardNuevasUbicaciones,
                subTitle = ubicacionesNuevasCount.toString(),
                image = R.drawable.ic_permisos,
                isLoading = loadingNuevasUbicaciones
            ) { exportacionViewModel.enviarPendientes(5) }
        }
    }
}