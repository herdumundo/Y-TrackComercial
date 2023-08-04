package com.example.y_trackcomercial.services.exportacion

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class ExportWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
            // Do the export here.
        Log.i("Mensaje","FUNCIONA")
        return Result.success()
    }
}

 fun scheduleExportWork(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequest.Builder(
        ExportWorker::class.java,
        15, // Intervalo de tiempo (cada 1 unidad de tiempo)
        TimeUnit.MINUTES // Unidad de tiempo (minutos)
    )
        .setConstraints(constraints)
        .build()
    WorkManager.getInstance(context).enqueue(workRequest)
}
