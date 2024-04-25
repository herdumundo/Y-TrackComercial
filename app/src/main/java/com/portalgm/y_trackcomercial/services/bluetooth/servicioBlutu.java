package com.portalgm.y_trackcomercial.services.bluetooth;

import android.content.Context;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;

public class servicioBlutu {
    public void printBluetooth(String  texto ){
        try {
            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 70f, 46);


            printer .printFormattedText(texto.toString());
           // printer .printFormattedText(texto.toString()); // es para imprimir 2 veces

            printer.disconnectPrinter();

        }
            catch (Exception e ){
            String vasd= e.toString();
        }
    }
}
