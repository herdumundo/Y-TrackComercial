package com.portalgm.y_trackcomercial.ui.updateApp


  import android.content.Context
  import android.content.Intent
  import android.util.Log
  import androidx.core.content.FileProvider
  import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
  import androidx.lifecycle.viewModelScope
   import com.portalgm.y_trackcomercial.R
  import com.portalgm.y_trackcomercial.repository.UpdateRepository
  import dagger.hilt.android.lifecycle.HiltViewModel
  import kotlinx.coroutines.Dispatchers
  import kotlinx.coroutines.flow.MutableStateFlow
  import kotlinx.coroutines.flow.StateFlow
  import kotlinx.coroutines.launch
  import kotlinx.coroutines.withContext
  import okhttp3.ResponseBody
  import java.io.File
  import java.io.FileOutputStream
  import javax.inject.Inject

@HiltViewModel
class UpdateAppViewModel   @Inject constructor(
    private val updateRepository: UpdateRepository,
    private val context: Context,
    ): ViewModel() {

    private val NOTIFICATION_ID = 1 // Puedes cambiar el valor según tus necesidades
    private val CHANNEL_ID = "update_channel"
    val channelName = context.getString(R.string.notification_channel_name)

    private val _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String> = _appVersion

    private val _showInstallDialog = MutableStateFlow(false)
    val showInstallDialog: StateFlow<Boolean> = _showInstallDialog

    fun onInstallButtonClicked() {
        _showInstallDialog.value = !_showInstallDialog.value
    }
    fun downloadAndSaveApk() {
        viewModelScope.launch {
            try {
                val responseBody: ResponseBody = updateRepository.downloadApk()
                withContext(Dispatchers.IO) {

                // Guardar el archivo APK en el almacenamiento externo
                val apkFile = File(context.getExternalFilesDir(null), "update.apk")
                val outputStream = FileOutputStream(apkFile)
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (responseBody.source().read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.close()
                // Verificar si la descarga se completó exitosamente
               if (apkFile.exists()) {
                   viewModelScope.launch(Dispatchers.Main) {
                       // Aquí puedes iniciar la instalación del APK usando un Intent
                       val uri = FileProvider.getUriForFile(
                           context,
                           "com.ytrack.y_trackcomercial.fileprovider", // Reemplaza con tu nombre de paquete
                           apkFile
                       )
                       val intent = Intent(Intent.ACTION_VIEW).apply {
                           setDataAndType(uri, "application/vnd.android.package-archive")
                           flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                       }
                       context.startActivity(intent)

                   }

               } else {
                    // La descarga no se completó exitosamente, maneja el escenario de error
                    Log.i("Mensaje","No Se descargo")
                }
                }

            } catch (e: Exception) {
                Log.i("Mensaje",e.toString())
            }
        } }

     fun checkAndRequestStoragePermission() {/*
        if (Build.VERSION.SDK_INT<= Build.VERSION_CODES.TIRAMISU) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:${context.packageName}")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Agrega esta línea
                context.startActivity(intent)
            }
        }*/
    }



}


