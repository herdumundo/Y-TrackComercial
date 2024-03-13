package com.portalgm.y_trackcomercial.services.bluetooth

import android.content.Context
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PdfDocumentAdapter(private val context: Context, private val path: String) : PrintDocumentAdapter() {
    override fun onWrite(pages: Array<out PageRange>, destination: ParcelFileDescriptor, cancellationSignal: CancellationSignal, callback: WriteResultCallback) {
        var input: FileInputStream? = null
        var output: FileOutputStream? = null

        try {
            input = FileInputStream(path)
            output = FileOutputStream(destination.fileDescriptor)

            val buf = ByteArray(1024)
            var bytesRead: Int

            while (input.read(buf).also { bytesRead = it } > 0 && !cancellationSignal.isCanceled) {
                output.write(buf, 0, bytesRead)
            }

            if (cancellationSignal.isCanceled) {
                callback.onWriteCancelled()
            } else {
                callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }
        } catch (e: Exception) {
            callback.onWriteFailed(e.message)
        } finally {
            try {
                input?.close()
                output?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onLayout(oldAttributes: PrintAttributes, newAttributes: PrintAttributes, cancellationSignal: CancellationSignal, callback: LayoutResultCallback, extras: Bundle) {
        if (cancellationSignal.isCanceled) {
            callback.onLayoutCancelled()
        } else {
            val pdi = PrintDocumentInfo.Builder("my_file_name.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .build()

            callback.onLayoutFinished(pdi, true)
        }
    }


}
