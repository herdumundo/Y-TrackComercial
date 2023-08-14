package com.ytrack.y_trackcomercial.ui.updateApp


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
 import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UpdateAppScreen(updateAppViewModel: UpdateAppViewModel) {
    var isDownloading by remember { mutableStateOf(false) }

    val showInstallDialog by updateAppViewModel.showInstallDialog.collectAsState()

    Scaffold(
        content = {
            Button(
                onClick = {
                    updateAppViewModel.checkAndRequestStoragePermission()

                    updateAppViewModel.onInstallButtonClicked()
                },
               // enabled = !isDownloading && versionCode.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                if (isDownloading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Text(text = "Descargar Actualización")
                }
            }

        }

    )

    if (showInstallDialog) {
        InstallConfirmationDialog(
            onConfirm = {
                // Ejecutar la instalación de la actualización
                updateAppViewModel.downloadAndSaveApk()
                updateAppViewModel.onInstallButtonClicked()
            },
            onCancel = {
                // Cancelar el diálogo
                updateAppViewModel.onInstallButtonClicked()
            }
        )
    }

}


@Composable
fun InstallConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Actualizar la app") },
        text = { Text("Deseas actualizar la app?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}


