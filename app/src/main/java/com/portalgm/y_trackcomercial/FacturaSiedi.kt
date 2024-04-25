package com.portalgm.y_trackcomercial

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.SiediPreferences
import com.portalgm.y_trackcomercial.util.generateString
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
@AndroidEntryPoint
class FacturaSiedi : AppCompatActivity() {
    private lateinit var mHandler: Handler
    val sharedData = SharedData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler(Looper.getMainLooper())
        initInput()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
                // PASO 2. RELLENAR DATOS EN ARCHIVO GENERADO.
                if (requestCode == 0x200) {
                    if (data == null) {
                        mHandler.post { Log.i("MensajeYtrack", "Error: NO DATA") }
                        return
                    }
                    val target = data.data ?: run {
                        mHandler.post { Log.i("MensajeYtrack", "Error: NO URI") }
                        return
                    }
                    // PASO 3: RELLENAR EL ARCHIVO.
                    fillInput(target)
                    // PASO 4. SOLICITAR PROCESAMIENTO DE DOCUMENTO ELECTRONICO.
                    initFirmar(target)
                }

                // PASO 5. LEER DATOS LUEGO DE PROCESAMIENTO DE DOCUMENTO ELECTRONICO.
                if (requestCode == 0x100) {
            if (data == null) {
                mHandler.post { Log.i("MensajeYtrack", "Error: NO DATA") }
                finish()  // Finalizar si no hay datos.
                return
            }

            data.getStringExtra("errors")?.let {
                mHandler.post {
                    Log.i("MensajeYtrackError", it)
                    val intent = Intent("com.portalgm.y_trackcomercial.DATA_PROCESSED")
                    intent.putExtra("datosXml", it)
                    intent.putExtra("datosQR_CDC", "null")
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                }
                finish()  // Finalizar si hay errores.
                return
            }

            data.data?.let { uri ->
                try {
                    contentResolver.openInputStream(uri)?.use { inputStream ->
                        val buffer = CharArray(4096)
                        val out = StringBuilder()
                        val reader = InputStreamReader(inputStream)
                        var numRead: Int
                        while (reader.read(buffer).also { numRead = it } > 0) {
                            out.append(buffer, 0, numRead)
                        }
                    //    mHandler.post { Log.i("MensajeYtrack",  out.toString()) }

                        mHandler.post {

                            //  Log.i("MensajeYtrack", out.toString())
                            val intent = Intent("com.portalgm.y_trackcomercial.DATA_PROCESSED")
                            intent.putExtra("datosXml", out.toString())
                            intent.putExtra("datosQR_CDC", data.getStringExtra("payload").toString())
                            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                            finish()  // Asegúrate de llamar a finish() después de enviar el broadcast.
                        }

                        finish()  // Finalizar después de procesar los datos.
                    }
                } catch (thr: Throwable) {

                    Log.e("MensajeYtrack", "error", thr)
                    mHandler.post { Log.i("MensajeYtrack", thr.message.orEmpty()) }

                    finish()  // Finalizar si ocurre una excepción.
                }
            } ?: finish()  // Finalizar si la URI es nula.
        }
    }

    // Realiza la escritura del archivo TXT en la URI
    private fun fillInput(target: Uri) {
        mHandler.post {
        //    Log.i("MensajeYtrack", "Creando datos ...")
        }

        try {
            val dataString = sharedData.txtSiedi.value!!
         //   Log.i("MensajeYtrack",dataString)
            contentResolver.openOutputStream(target)?.use { os ->
                os.write(dataString.toByteArray())

            }
        } catch (thr: Throwable) {
            Log.e("test", "Error", thr)
            mHandler.post { Log.i("MensajeYtrack", thr.message.orEmpty()) }
        }

        try {
            val data = read(target)
            mHandler.post {
                //Log.i("MensajeYtrack", data)
            }
        } catch (thr: Throwable) {
            Log.e("MensajeYtrack", "ERROR", thr)
            mHandler.post { Log.i("MensajeYtrack", thr.message.orEmpty()) }

        }
    }
    private fun initFirmar(target: Uri) {
        // Intent para compartir.
        val intent = Intent()
        intent.action = "py.com.sepsa.siediapp.intent.action.FIRMAR_DTE"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.setDataAndType(target, "text/csv")
        ActivityCompat.startActivityForResult(this, intent, 0x100, Bundle())
    }

    // Obtiene el contenido del URI en string
    @Throws(IOException::class)
    private fun read(target: Uri): String {
        contentResolver.openInputStream(target)?.use { inputStream ->
            val buffer = CharArray(4096)
            val out = StringBuilder()
            val reader = InputStreamReader(inputStream)
            var numRead: Int
            while (reader.read(buffer).also { numRead = it } > 0) {
                out.append(buffer, 0, numRead)
            }
            return out.toString()
        } ?: throw IOException("No se pudo abrir el InputStream para el URI: $target")
    }
    fun initInput() {
        mHandler.post {
        //    Log.i("MensajeYtrack", "Solicitando datos ...")
        }
        val paquete = packageName
        // PASO 1. SOLICITAR LA CREACION DE UN NUEVO ARCHIVO.
        val intent = Intent()
        intent.action = "py.com.sepsa.siediapp.intent.action.CREAR_ARCHIVO"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra("packageName", paquete)
        ActivityCompat.startActivityForResult(this, intent, 0x200, Bundle())
    }


}
