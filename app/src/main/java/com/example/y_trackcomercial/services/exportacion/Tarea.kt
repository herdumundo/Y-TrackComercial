package com.example.y_trackcomercial.services.exportacion

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleExportWorker(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequest.Builder(
        ExportWorker::class.java,
        15, // Intervalo de tiempo (cada 15 minutos)
        TimeUnit.MINUTES ,// Unidad de tiempo (minutos)

    )
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}