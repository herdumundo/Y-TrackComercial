package com.portalgm.y_trackcomercial.util

import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Environment
import android.util.Log
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfWriter

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/*private fun createPDFDocument(): PdfDocument {
    val pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/invoice.pdf"
    val pdfWriter = PdfWriter(pdfPath)
    return PdfDocument(pdfWriter)
}*/

fun generarFacturaPDF(context: Context, nombreArchivo: String, productos: List<Producto>, total: Double) {
    val documento = Document()

    try {
        val directorio = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "facturas") // Cambia "facturas" al directorio deseado
        directorio.mkdirs()
        val archivoPDF = File(directorio, nombreArchivo)

        val writer = PdfWriter.getInstance(documento, FileOutputStream(archivoPDF))
        documento.open()

        // Título de la factura
        val titulo = Paragraph("Factura de Productos")
        documento.add(titulo)

        // Información de los productos
        for (producto in productos) {
            val lineaProducto = Paragraph("${producto.nombre}: ${producto.precio}")
            documento.add(lineaProducto)
        }

        // Total
        val lineaTotal = Paragraph("Total: $total")
        documento.add(lineaTotal)

        documento.close()
        writer.close()

        Log.i("MensajeTest","Factura generada exitosamente en ${archivoPDF.absolutePath}")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



data class Producto(val nombre: String, val precio: Double)

private fun printPDFToBluetoothPrinter(socket: BluetoothSocket, pdfFile: File) {
    try {
        val outputStream: OutputStream = socket.outputStream
        val pdfReader = PdfReader(FileInputStream(pdfFile))

        val document = Document()
        val writer = PdfWriter.getInstance(document, outputStream)

        document.open()
        val content = writer.directContent
        val page = writer.getImportedPage(pdfReader, 1)

        content.addTemplate(page, 0f, 0f)

        document.close()
        outputStream.flush()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

