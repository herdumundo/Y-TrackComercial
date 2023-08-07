package com.example.y_trackcomercial.services.exportacion

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Constraints
 import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.y_trackcomercial.repository.registroRepositories.logRepositories.AuditTrailRepository
import com.example.y_trackcomercial.usecases.auditLog.CountLogPendientesUseCase
import com.example.y_trackcomercial.usecases.exportacionAuditTrail.CountAuditTrailUseCase
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject
class ExportWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val auditTrailRepository: AuditTrailRepository,
) : Worker(context, params,) {
    //    lateinit var  countLogPendientesUseCase: CountAuditTrailUseCase
    override fun doWork(): Result = runBlocking {
        try {
            // Obtener el recuento de registros pendientes utilizando el caso de uso
            val pendientes = 0//auditTrailRepository.getAuditTrailCount()

            // Realizar la lógica de exportación según el recuento de registros pendientes
           /* if (pendientes > 0  ) {
                // Si hay registros pendientes, realizar la exportación
                // Realizar la lógica para exportar los datos a través de la API
                // MiClase.exportarDatos()
                Log.i("Mensaje","SE EJECUTA $pendientes ")
            }*/

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
//Log.i("Mensaje","SE EJECUTA $pendientes")

