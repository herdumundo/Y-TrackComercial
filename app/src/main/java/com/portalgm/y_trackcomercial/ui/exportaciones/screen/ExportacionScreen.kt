package com.portalgm.y_trackcomercial.ui.exportaciones.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.cardViewLoadingTablas
import com.portalgm.y_trackcomercial.ui.exportaciones.viewmodel.ExportacionViewModel
import com.portalgm.y_trackcomercial.ui.tablasRegistradas.TablaInfo

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
    val newPassCount by exportacionViewModel.newPassCount.observeAsState()
    val pendientesOinvCount by exportacionViewModel.pendientesOinvCount.observeAsState()
    val pendientesNroNuevoFacturaCount by exportacionViewModel.nuevoNroFacturaCount.observeAsState()
    val anulacionFacturaCount by exportacionViewModel.anulacionFacturaCount.observeAsState()
    val facturasNoProcesadasSapCount by exportacionViewModel.facturasNoProcesadasSapCount.observeAsState()

    val loadingVisitas by exportacionViewModel.loadingVisitas.observeAsState(false)
    val loadingAuditTrail by exportacionViewModel.loadingAuditTrail.observeAsState(false)
    val loadingLog by exportacionViewModel.loadingLog.observeAsState(false)
    val loadingMovimientos by exportacionViewModel.loadingMovimientos.observeAsState(false)
    val loadingNuevasUbicaciones by exportacionViewModel.loadingNuevasUbicaciones.observeAsState(false)
    val loadingNewPass by exportacionViewModel.loadingNewPass.observeAsState(false)
    val loadingOinv by exportacionViewModel.loadingOinv.observeAsState(false)
    val loadingNroNuevoFactura by exportacionViewModel.loadingonuevoNroFacturaCount.observeAsState(false)
    val loadingAnulacionFactura by exportacionViewModel.loadingAnulacionFacturaCount.observeAsState(false)
    val loadingFacturasNoProcesadasSap by exportacionViewModel.loadingFacturasNoProcesadasSap.observeAsState(false)

    val colorCardFacturasNoProcesadasSap = if (facturasNoProcesadasSapCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardFacturasNoProcesadasSap = if (facturasNoProcesadasSapCount == 0) "Procesos sin pendientes" else "Procesar facturas pendientes a SAP"


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

    val colorCardNuevaContraseña = if (newPassCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardNuevoPass= if (newPassCount == 0) "Nueva contraseña sin pendientes" else "Exportar Nueva Contraseña"

    val colorCardOinv = if (pendientesOinvCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeCardOinv= if (pendientesOinvCount == 0) "Facturas sin pendientes" else "Exportar Facturas"

    val colorNuevoNroFactura = if (pendientesNroNuevoFacturaCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeNuevoNroFactura= if (pendientesNroNuevoFacturaCount == 0) "Nuevo nro de factura sin pendientes" else "Exportar Nuevo nro de Facturas"

    val colorFacturaAnulacion = if (anulacionFacturaCount == 0) Color(0xFF126300) else Color(0xFFB90000)
    val mensajeFacturaAnulacion= if (anulacionFacturaCount == 0) "Anulacion de Facturas sin pendientes" else "Exportar Anulacion de Facturas"


    val tablaInfo = listOf(
        TablaInfo(colorCard,mensajeCard, visitasCount, R.drawable.ic_clock_permiso, loadingVisitas, 1, "Enviando visitas..."),
        TablaInfo(colorCardAuditTrail,mensajeCardAuditTrail, auditTrailCount, R.drawable.ic_step, loadingAuditTrail, 2, "Enviando Auditoria Trail..."),
        TablaInfo(colorCardLog,mensajeCardLog, logCount, R.drawable.ic_log_activity, loadingLog, 3, "Enviando Log de actividades..."),
        TablaInfo(colorCardMovimientos,mensajeCardMovimientos, movimientosCount, R.drawable.ic_moving, loadingMovimientos, 4, "Enviando Movimientos..."),
        TablaInfo(colorCardNuevasUbicaciones,mensajeCardNuevasUbicaciones, ubicacionesNuevasCount, R.drawable.ic_permisos, loadingNuevasUbicaciones, 5, "Enviando Nuevas Ubicaciones..."),
        TablaInfo(colorCardNuevaContraseña,mensajeCardNuevoPass, newPassCount, R.drawable.ic_permisos, loadingNewPass, 6, "Enviando Nueva Clave..."),
        TablaInfo(colorCardOinv,mensajeCardOinv, pendientesOinvCount, R.drawable.ic_lotes, loadingOinv, 7, "Enviando Facturas..."),
        TablaInfo(colorNuevoNroFactura,mensajeNuevoNroFactura, pendientesNroNuevoFacturaCount, R.drawable.ic_lotes, loadingNroNuevoFactura, 8, "Enviando Nuevo nro de Facturas..."),
        TablaInfo(colorFacturaAnulacion,mensajeFacturaAnulacion, anulacionFacturaCount, R.drawable.ic_lotes, loadingAnulacionFactura, 9, "Enviando Anulacion de Facturas..."),
        TablaInfo(colorCardFacturasNoProcesadasSap,mensajeCardFacturasNoProcesadasSap, facturasNoProcesadasSapCount, R.drawable.ic_lugar, loadingFacturasNoProcesadasSap, 10, "Buscando facturas procesadas a SAP..."),

        )
    LazyColumn {
        items(tablaInfo) { info ->
            cardViewLoadingTablas(
                textoLoading = info.textoLoading,
                color = info.color,
                title = info.title,
                subTitle = info.count.toString(),
                image = info.image,
                isLoading = info.isLoading
            ) {
                exportacionViewModel.enviarPendientes(info.updateId)
            }
        }
    }
}
data class TablaInfo(
    val color : Color,
    val title: String,
    val count:  Int? ,
    val image: Int,
    val isLoading: Boolean,
    val updateId: Int,
    val textoLoading: String = "Cargando..." // Ejemplo de un valor por defecto
)