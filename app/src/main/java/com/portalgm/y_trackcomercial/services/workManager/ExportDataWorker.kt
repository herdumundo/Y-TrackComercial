package com.portalgm.y_trackcomercial.services.workManager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
 import androidx.work.WorkerParameters
import com.portalgm.y_trackcomercial.core.di.ForWorker
import com.portalgm.y_trackcomercial.usecases.newPass.ExportarNewPassPendientesUseCase
import com.portalgm.y_trackcomercial.usecases.newPass.GetNewPassPendientesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject
@HiltWorker
class ExportDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
   // @ForWorker private val exportarNewPassPendientesUseCase: ExportarNewPassPendientesUseCase,

): CoroutineWorker(appContext, workerParams) {
    @Inject
    lateinit var getNewPassPendientesUseCase: GetNewPassPendientesUseCase

    override suspend fun doWork(): Result {
         try {
            Log.i("Tarea", "Ajuste realizado ")
             val lotesPendientes = getNewPassPendientesUseCase.getPendientes()
           // exportarNewPassPendientesUseCase.enviarPendientes(lotesPendientes)
        } catch (e: Exception) {
            Log.i("Tarea", e.toString())
        }
        return Result.success()
    }
}
