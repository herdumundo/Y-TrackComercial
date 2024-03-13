package com.portalgm.y_trackcomercial.services.bluetooth

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.os.Binder
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.util.Log
import com.portalgm.y_trackcomercial.R
import java.io.File
import java.io.IOException
import java.io.OutputStream

class BluetoothPrintService : Service() {
    private val binder = LocalBinder()
    private var bluetoothConnector: BluetoothConnector? = null

    inner class LocalBinder : Binder() {
        fun getService(): BluetoothPrintService = this@BluetoothPrintService
    }

    override fun onBind(intent: Intent): IBinder = binder

    fun startPrinting(deviceAddress: String) {
        bluetoothConnector = BluetoothConnector(deviceAddress)

        try {
            val socket = bluetoothConnector?.connect()
            val outputStream = socket?.outputStream

            // Llama a la función que procesa y envía el PDF, pasando outputStream como parámetro
            printPdfFromRaw(R.raw.factura, outputStream!!)

            outputStream?.flush()
            Log.d("BluetoothPrintService", "PDF enviado a la impresora")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("BluetoothPrintService", "Error al enviar el PDF: ", e)
        } finally {
            try {
                bluetoothConnector?.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun bitmapToPrinterCommands(originalBitmap: Bitmap): ByteArray {
        // Define el ancho máximo para la impresora de 80mm a 203 DPI, ajusta si es necesario
        val maxWidth = 384 // Ajusta esto según tu impresora
        val maxHeight = (maxWidth.toFloat() / originalBitmap.width * originalBitmap.height).toInt()

        // Redimensiona el bitmap
        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, maxWidth, maxHeight, true)

        // Usa resizedBitmap para obtener los píxeles
        val width = resizedBitmap.width
        val height = resizedBitmap.height
        val pixels = IntArray(width * height)
        resizedBitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val bytes = ByteArray(width * height)
        // Convertir cada píxel a un byte
        for (i in pixels.indices) {
            // Este es un ejemplo muy básico, deberás adaptar esto a cómo tu impresora espera los datos
            bytes[i] = if (pixels[i] == -0x1) 0 else 1
        }

        // Aquí necesitarías agregar la lógica para formatear estos bytes en comandos ESC/POS
        return bytes
    }
     fun printPdfFromRaw(pdfResourceId: Int,outputStream: OutputStream) {
        // Paso 1: Obtener el InputStream del PDF
        val inputStream = applicationContext.resources.openRawResource(pdfResourceId)

        // Paso 2: Escribir el contenido del InputStream a un archivo temporal
        val file = File.createTempFile("temp_factura", ".pdf", applicationContext.cacheDir)
        file.outputStream().use {
            inputStream.copyTo(it)
        }

        // Paso 3: Utilizar PdfRenderer para convertir el PDF a Bitmap
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)

        for (i in 0 until pdfRenderer.pageCount) {
            val page = pdfRenderer.openPage(i)
            val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()

            // Enviar el bitmap a la impresora
            // Aquí debes llamar a la función que convierte el bitmap a comandos de la impresora y enviarlos
            val commands = bitmapToPrinterCommands(bitmap)  // Implementa esta función según tu impresora
            sendToPrinter(bitmapToPrinterCommands(bitmap), outputStream)
        }

        pdfRenderer.close()
        fileDescriptor.close()
        file.delete()  // Eliminar el archivo temporal
    }


    fun sendToPrinter(commands: ByteArray, outputStream: OutputStream?) {
        try {
            outputStream?.write(commands)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}
