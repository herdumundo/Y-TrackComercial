package com.portalgm.y_trackcomercial.services.workManager
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
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
import javax.inject.Inject

// Importa tus casos de uso aquí...
/*
class ExportDataWorkerFactory @Inject constructor(
    private val countAuditTrailUseCase: CountAuditTrailUseCase,
    private val countCantidadPendientes: CountCantidadPendientes,
    private val countLogPendientesUseCase: CountLogPendientesUseCase,
    private val countMovimientoUseCase: CountMovimientoUseCase,
    private val getVisitasPendientesUseCase: GetVisitasPendientesUseCase,
    private val getAuditTrailPendienteUseCase: GetAuditTrailPendienteUseCase,
    private val getLogPendienteUseCase: GetLogPendienteUseCase,
    private val getMovimientoPendientesUseCase: GetMovimientoPendientesUseCase,
    private val enviarVisitasPendientesUseCase: EnviarVisitasPendientesUseCase,
    private val enviarAuditTrailPendientesUseCase: EnviarAuditTrailPendientesUseCase,
    private val enviarLogPendientesUseCase: EnviarLogPendientesUseCase,
    private val enviarMovimientoPendientesUseCase: EnviarMovimientoPendientesUseCase,
    private val enviarNuevasUbicacionesPendientesUseCase: ExportarNuevasUbicacionesPendientesUseCase,
    private val getNuevasUbicacionesPendientesUseCase: GetNuevasUbicacionesPendientesUseCase,
    private val countUbicacionesNuevasPendientesUseCase: CountUbicacionesNuevasPendientesUseCase
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ExportDataWorker::class.java.name -> {
                val worker = ExportDataWorker(appContext, workerParameters)
                // Asignar dependencias
                worker.countAuditTrailUseCase = countAuditTrailUseCase
                worker.countCantidadPendientes = countCantidadPendientes
                worker.countLogPendientesUseCase = countLogPendientesUseCase
                worker.countMovimientoUseCase = countMovimientoUseCase
                worker.getVisitasPendientesUseCase = getVisitasPendientesUseCase
                worker.getAuditTrailPendienteUseCase = getAuditTrailPendienteUseCase
                worker.getLogPendienteUseCase = getLogPendienteUseCase
                worker.getMovimientoPendientesUseCase = getMovimientoPendientesUseCase
                worker.enviarVisitasPendientesUseCase = enviarVisitasPendientesUseCase
                worker.enviarAuditTrailPendientesUseCase = enviarAuditTrailPendientesUseCase
                worker.enviarLogPendientesUseCase = enviarLogPendientesUseCase
                worker.enviarMovimientoPendientesUseCase = enviarMovimientoPendientesUseCase
                worker.enviarNuevasUbicacionesPendientesUseCase = enviarNuevasUbicacionesPendientesUseCase
                worker.getNuevasUbicacionesPendientesUseCase = getNuevasUbicacionesPendientesUseCase
                worker.countUbicacionesNuevasPendientesUseCase = countUbicacionesNuevasPendientesUseCase
                worker
            }
            else -> null
        }
    }
}
*/