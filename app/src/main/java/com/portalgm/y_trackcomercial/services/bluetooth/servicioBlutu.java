package com.portalgm.y_trackcomercial.services.bluetooth;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;



public class servicioBlutu {

    public void printBluetooth(){
        try {
/**
 * FUNCION PARA LLAMAR A LA IMPRESION
 *
 *        val bluetoothPrinter = servicioBlutu()
 *         bluetoothPrinter.printBluetooth()*/
            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32);
            printer
                    .printFormattedText(
                    //        "[C]<qrcode size='50'>https://ekuatia.set.gov.py/consultas/qr?nVersion=150&Id=01800027540001006000497222023060814631118434&dFeEmiDE=323032332d30362d30385430393a31303a3032&dRucRec=80131132&dTotGralOpe=236250&dTotIVA=11250&cItems=1&DigestValue=6c4138454934566a616a534838436957786674723944686f544943746f6c317a44693046655945374e66343d&IdCSC=1&cHashQR=da8db60c26bda270e9305a168453427c1a4a626b9c31b8e6c58798b310656069</qrcode>"

                            "[L]\n" +
                                    "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99e\n" +
                                    "[L]  + Size : S\n" +
                                    "[L]\n" +
                                    "[L]<b>AWESOME HAT</b>[R]24.99e\n" +
                                    "[L]  + Size : 57/58\n" +
                                    "[L]\n" +
                                    "[C]--------------------------------\n" +
                                    "[R]TOTAL munición :[R]34.98e\n" +
                                    "[R]TAX :[R]4.23e\n" +
                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<font size='tall'>Customer :</font>\n" +
                                    "[L]Raymond DUPONT\n" +
                                    "[L]5 rue des girafes\n" +
                                    "[L]31547 PERPETES\n" +
                                    "[L]Tel : +33801201456\n" +
                                    "[L]\n"
                     );
        }
        catch (Exception e ){
            String vasd= e.toString();
        }
    }


}
