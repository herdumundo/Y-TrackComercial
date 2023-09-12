package com.portalgm.y_trackcomercial.services.exportacion

import com.portalgm.y_trackcomercial.data.api.request.EnviarAuditoriaTrailRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeActividadesRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeMovimientosRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarLotesDeUbicacionesNuevasRequest
import com.portalgm.y_trackcomercial.data.api.request.EnviarVisitasRequest
import com.portalgm.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.EnviarLogPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.auditLog.GetLogPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.EnviarAuditTrailPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionAuditTrail.GetAuditTrailPendienteUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.CountCantidadPendientes
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.EnviarVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.exportacionVisitas.GetVisitasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.CountMovimientoUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.EnviarMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.inventario.GetMovimientoPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.CountUbicacionesNuevasPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.ExportarNuevasUbicacionesPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.nuevaUbicacion.GetNuevasUbicacionesPendientesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun ExportarDatos(
    countAuditTrailUseCase: CountAuditTrailUseCase,
    countCantidadPendientes: CountCantidadPendientes,
    countLogPendientesUseCase: CountLogPendientesUseCase,
    countMovimientoUseCase: CountMovimientoUseCase,
    getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    getLogPendienteUseCase: GetLogPendienteUseCase,
    getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase,
    enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
    enviarLogPendientesUseCase: EnviarLogPendientesUseCase,
    enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase,
    enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase,
    getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase,
    countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase
) {
// Lanza una corrutina para la exportación de datos
   CoroutineScope(Dispatchers.IO).launch {
   do {
       val currentTime = Calendar.getInstance().time
       val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
       val currentTimeString = dateFormat.format(currentTime)
       // Verificar si la hora actual está entre las 07:00 y las 18:00
       if (currentTimeString >= "07:00" && currentTimeString <= "19:00") {
           // Realiza la lógica de exportación aquí
           exportarDatos(
               countAuditTrailUseCase,
               countCantidadPendientes,
               countLogPendientesUseCase,
               countMovimientoUseCase,
               getVisitasPendientesUseCase,
               getAuditTrailPendienteUseCase,
               getLogPendienteUseCase,
               getMovimientoPendientesUseCase,
               enviarVisitasPendientesUseCase,
               enviarAuditTrailPendientesUseCase,
               enviarLogPendientesUseCase,
               enviarMovimientoPendientesUseCase,
               enviarNuevasUbicacionesPendientesUseCase,
               getNuevasUbicacionesPendientesUseCase,
               countUbicacionesNuevasPendientesUseCase
           )
       }

      delay(10 * 60 * 1000) // 10 minutos en milisegundos

   } while (true)
}
}

suspend fun exportarDatos(
    countAuditTrailUseCase: CountAuditTrailUseCase,
    countCantidadPendientes: CountCantidadPendientes,
    countLogPendientesUseCase: CountLogPendientesUseCase,
    countMovimientoUseCase: CountMovimientoUseCase,
    getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    getLogPendienteUseCase: GetLogPendienteUseCase,
    getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase,
    enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
    enviarLogPendientesUseCase: EnviarLogPendientesUseCase,
    enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase,
    enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase,
    getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase,
    countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase
) {
    if (countUbicacionesNuevasPendientesUseCase.CountPendientes() > 0) {
        val nuevasUbicacionesPendientes =
            getNuevasUbicacionesPendientesUseCase.GetPendientes()
        val enviarNuevaUbicacionRequest =
            EnviarLotesDeUbicacionesNuevasRequest(nuevasUbicacionesPendientes)
        enviarNuevasUbicacionesPendientesUseCase.enviarPendientes(
            enviarNuevaUbicacionRequest
        )
    }

    if (countCantidadPendientes.ContarCantidadPendientes() > 0) {
    val visitasPendientes =
       getVisitasPendientesUseCase.getVisitasPendientes()
   val enviarVisitasRequest = EnviarVisitasRequest(visitasPendientes)
   enviarVisitasPendientesUseCase.enviarVisitasPendientes(
       enviarVisitasRequest
   )
}

if (countAuditTrailUseCase.CountPendientesExportacion() > 0) {
    val auditTrailPendientes =
       getAuditTrailPendienteUseCase.getAuditTrailPendientes()
   val enviarAuditTrailRequest =
       EnviarAuditoriaTrailRequest(auditTrailPendientes)
   enviarAuditTrailPendientesUseCase.enviarAuditTrailPendientes(
       enviarAuditTrailRequest
   )
}
if (countLogPendientesUseCase.CountPendientes() > 0) {
    val auditLogPendientes =
       getLogPendienteUseCase.getAuditLogPendientes()
   val enviarAuditLogRequest =
       EnviarLotesDeActividadesRequest(auditLogPendientes)
   enviarLogPendientesUseCase.enviarLogPendientes(enviarAuditLogRequest)
}
    if (countMovimientoUseCase.CountPendientes() > 0) {
        val movimientosPendientes =
            getMovimientoPendientesUseCase.GetPendientes()
        val enviarmovimientosRequest =
            EnviarLotesDeMovimientosRequest(movimientosPendientes)
        enviarMovimientoPendientesUseCase.enviarPendientes(
            enviarmovimientosRequest
        )
    }






//Log.i("Mensaje", "DATOS EXPORTADOS")
}
