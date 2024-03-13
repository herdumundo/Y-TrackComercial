package com.portalgm.y_trackcomercial.services.bluetooth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.portalgm.y_trackcomercial.R;


public class servicioBlutu {

    public void printBluetooth( Context context){
        try {
/**
 * FUNCION PARA LLAMAR A LA IMPRESION
 *
 *        val bluetoothPrinter = servicioBlutu()
 *         bluetoothPrinter.printBluetooth()*/



            EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 70f, 46);
            printer
                    .printFormattedText(
                            /*"[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,
                                    context.getApplicationContext().getResources().getDrawableForDensity(R.drawable.ytrack2, DisplayMetrics.DENSITY_MEDIUM))+
                                    "</img>\n" +*/
                                    "[L]\n" +
                                            "[C]<u><font size='small'>VIMAR y CIA S.A</font></u>\n" +
                                            "[C]<u><font size='small'>RUC: 80002754-0</font></u>\n" +
                                            "[C]<u><font size='small'>Comercio al por mayor de </font></u>\n" +
                                            "[C]<u><font size='small'>comestibles, excepto carnes</font></u>\n \n\n" +
                                            "[L]<b>Timbrado Nro: 16056231</b>   \n" +
                                            "[L]<b>Valida desde : 01/12/2022</b>   \n" +
                                            "[L]<b>Condicion de venta : CONTADO</b>   \n" +
                                            "[L]<b>Moneda: Guarani</b>   \n" +
                                            "[L]<b>IVA INCLUIDO</b>   \n" +
                                            "[C]===============================================\n" +
                                            "[L]<b>Nro de factura: 002-001-002700</b>   \n" +
                                            "[L]<b>Fecha factura: 14/03/2024</b>   \n" +
                                    "[L]\n" +
                                    "[C]================================\n" +
                                    "[L]\n" +
                                    "[L]<b>Impu</b>  <b>Cantidad</b>    <b>Precio</b>   [R]<b>Total</b>     \n\n" +
                                    "[L]<b>7840061000150-HUEVOS YEMITA PAQ 180 A D</b>        " +
                                    "[L]<b>5%</b>    <b>25</b>          <b>174.000</b>  [R]<b>4.350.000</b>\n" +
                                    "[C]-----------------------------------------\n" +
                                    "[L]<b>7840061000150-HUEVOS YEMITA PAQ 180 A A</b>        " +
                                    "[L]<b>5%</b>    <b>30</b>          <b>183.000</b>  [R]<b>4.980.000</b>\n" +
                                    "[C]-----------------------------------------\n" +
                                    "[L]<b>7840061000150-HUEVOS YEMITA PAQ 180 A S</b>        " +
                                    "[L]<b>5%</b>    <b>25</b>          <b>183.000</b>  [R]<b>575.000</b>\n" +

                                    "[C]-----------------------------------------\n" +
                                    "[L]<font size='small'> TOTAL A PAGAR:              [L]<b>Gs. </b>[R]<b>34.575.000</b></font>\n" +
                                    "[C]-----------------------------------------\n" +
                                    "[L]<font size='small'> Total Gravadas 10%:         [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                                    "[L]<font size='small'> Total Gravadas 5%:          [L]<b>Gs. </b>[R]<b>34.575.000</b></font>\n" +
                                    "[L]<font size='small'> Total Exentas%:             [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                                    "[C]-----------------------------------------\n" +
                                    "[L]<font size='small'> Total IVA 10%:              [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                                    "[L]<font size='small'> Total IVA 5%:               [L]<b>Gs. </b>[R]<b>575.000</b></font>\n" +
                                    "[L]<font size='small'> Total IVA:                  [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                                    "[C]-----------------------------------------\n\n\n" +
                                    "[C]<u><font size='small'>*** Gracias por su compra ***</font></u>\n" +
                                    "[L]\n"+
                                    "[C]<qrcode size='40'>https://ekuatia.set.gov.py/consultas/qr?nVersion=150&Id=01800027540002001008270022024031416229278373&dFeEmiDE=323032342d30332d31345430343a32363a3032&dRucRec=3602936&dTotGralOpe=360000&dTotIVA=17142.85714286&cItems=1&DigestValue=75465445694c47414f5362616874574772503245656f6e7368673777516c31612f664a4f336435665a77633d&IdCSC=1&cHashQR=de9aed1b11e58c3920ef1c243437d0ecdcffb272324ab2d913f3b0db6b269d6d</qrcode>"
                    );
        }
        catch (Exception e ){
            String vasd= e.toString();
        }
    }


}
