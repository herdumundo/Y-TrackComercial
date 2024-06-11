package com.portalgm.y_trackcomercial.services.bluetooth

import android.content.Context
import android.util.DisplayMetrics
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.portalgm.y_trackcomercial.R

class servicioBluetooth(private val context: Context) {
    fun imprimir(texto: String): ImpresionResultado {
        try {

            val printerConnection = BluetoothPrintersConnections.selectFirstPaired()
            val printer = EscPosPrinter(printerConnection, 203, 70f, 46)
            val img= "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,   context.getResources().getDrawableForDensity(R.drawable.imageimpresion, DisplayMetrics.DENSITY_LOW))+"</img>\n"
            val texto=img+texto
            printerConnection!!.write(byteArrayOf(0x1B, 0x40))
            printerConnection.send()
            printer.printFormattedText(texto)
            printerConnection.write(byteArrayOf(0x1B, 0x40))
            printer.disconnectPrinter()
            return ImpresionResultado.Exito("Registrado con éxito!")
        } catch (e: Exception) {
            return ImpresionResultado.Error("Registrado con éxito! \n  Pero hubo un error al imprimir: ${e.message}")
        }
    }
}sealed class ImpresionResultado {
    data class Exito(val mensaje: String) : ImpresionResultado()
    data class Error(val mensaje: String) : ImpresionResultado()
}