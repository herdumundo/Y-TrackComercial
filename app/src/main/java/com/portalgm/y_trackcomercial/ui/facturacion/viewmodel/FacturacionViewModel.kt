package com.portalgm.y_trackcomercial.ui.facturacion.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBluetooth
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvUseCase
import com.portalgm.y_trackcomercial.util.Event
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.generateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class OinvViewModel @Inject constructor(
    private val getOinvUseCase: GetOinvUseCase,
     ) : ViewModel() {
    val sharedData = SharedData.getInstance()
    // Evento que el Activity puede observar
    private val _initFacturaEvent = MutableLiveData<Event<Unit>>()
    val initFacturaEvent: LiveData<Event<Unit>> = _initFacturaEvent

    // Lógica para iniciar el proceso de factura
    fun initFactura() {
       // SharedData.getInstance().setDebeContinuar(false) // Para desactivarlo
         getListaTextSiedi()
    }

    /**
     * PASO 1*/
    fun getListaTextSiedi() {
        viewModelScope.launch {
            try {
             //   val gson2 = Gson()
             //   val gson = Gson()
              //  val listType = object : TypeToken<List<OinvPosWithDetails>>() {}.type

                val lista = getOinvUseCase.execute(0)
               // val json = gson2.toJson(lista)
               // val oinvList: List<OinvPosWithDetails> = gson.fromJson(json, listType)
               // Log.d("oinvList", oinvList.toString())

                generarStringSiedi(lista)
            } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }

    fun generarStringSiedi(oinvList: List<OinvPosWithDetails>) {

        val random = Random.Default
        val ranNum = random.nextInt(100000001, 1000000000)  // El límite superior es exclusivo
        val lines118 = mutableListOf<String>()
        val lines119 = mutableListOf<String>()
        val lines120 = mutableListOf<String>()
        val lines128 = mutableListOf<String>()
        // Iterar sobre cada elemento de la lista y añadir la cadena al StringBuilder
        val stringBuilder = StringBuilder()
        oinvList.forEach { oinvDetails ->
            val details = oinvDetails.details
            /**1*/
            stringBuilder.append("1;")
            stringBuilder.append(/*1.1*/"INVOICE;")
            stringBuilder.append(/*1.2*/"${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)} 10:42:33;")
            stringBuilder.append(/*1.3*/"ORIGINAL;")
            stringBuilder.append(/*1.4*/"${oinvDetails.oinvPos.timb}-${
                oinvDetails.oinvPos.numAtCard!!.replace(
                    "-",
                    ""
                )
            };"
            )
            stringBuilder.append(/*1.5*/";")
            stringBuilder.append(/*1.6*/"80002754-0;")
            stringBuilder.append(/*1.7*/"RUC;")
            stringBuilder.append(/*1.8*/"PYG;")
            stringBuilder.append(/*1.9*/";")
            stringBuilder.append(/*1.10*/";")
            stringBuilder.append(/*1.11*/";")
            stringBuilder.append(/*1.12*/";")
            stringBuilder.append(/*1.13*/";")
            stringBuilder.append(/*1.14*/";")
            stringBuilder.append(/*1.15*/";")
            stringBuilder.append(/*1.16*/";")
            stringBuilder.append(/*1.17*/";")
            stringBuilder.append(/*1.18*/";")
            stringBuilder.append(/*1.19*/";")
            stringBuilder.append(/*1.20*/";")
            stringBuilder.append(/*1.21*/";")
            stringBuilder.append(/*1.22*/"${oinvDetails.oinvPos.licTradNum};")
            stringBuilder.append(/*1.23*/"RUC;")
            stringBuilder.append(/*1.24*/";")
            stringBuilder.append(/*1.25*/"80002754-0;")
            stringBuilder.appendLine(/*1.26*/"RUC;")

            /**2*/
            var line = 1
            for (detail in details) {
                stringBuilder.append("2;")
                stringBuilder.append(/*2.1*/    "${line};")
                stringBuilder.append(/*2.2*/    "${line/*CODBARRA*/};")
                stringBuilder.append(/*2.3*/    "${detail.quantity};")
                stringBuilder.append(/*2.4*/    "PCS;")
                stringBuilder.append(/*2.5*/    "${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)};")
                stringBuilder.append(/*2.6*/    "SP;")
                stringBuilder.append(/*2.7*/    "${detail.itemName};")
                stringBuilder.append(/*2.8*/    "${detail.precioUnitSinIva};")
                stringBuilder.append(/*2.9*/    "PCS;")
                stringBuilder.append(/*2.10*/   ";")
                stringBuilder.append(/*2.11*/   ";")
                stringBuilder.append(/*2.12*/   ";")
                stringBuilder.append(/*2.13*/   ";")
                stringBuilder.append(/*2.14*/   ";")
                stringBuilder.append(/*2.15*/   ";")
                stringBuilder.append(/*2.16*/   ";")
                stringBuilder.append(/*2.17*/   "VALUE_ADDED_TAX;")
                stringBuilder.append(/*2.18*/   "${detail.totalSinIva};")
                stringBuilder.append(/*2.19*/   "${detail.totalIva};")
                stringBuilder.append(/*2.20*/   "${oinvDetails.oinvPos.iva};")
                stringBuilder.append(/*2.21*/   "STANDARD_RATE;")
                stringBuilder.append(/*2.22*/   ";")
                stringBuilder.appendLine(/*2.23*/   ";")
                line++
            }
            /**5*/
            stringBuilder.append("5;")
            stringBuilder.append(/*5.1*/"1023810;")
            stringBuilder.append(/*5.2*/"51190;")
            stringBuilder.append(/*5.3*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.4*/"1023810;")
            stringBuilder.append(/*5.5*/"51190;")
            stringBuilder.append(/*5.6*/"STANDARD_RATE;")
            stringBuilder.append(/*5.7*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.8*/"0;")
            stringBuilder.append(/*5.9*/"0;")
            stringBuilder.append(/*5.10*/"10;")
            stringBuilder.append(/*5.11*/"STANDARD_RATE;")
            stringBuilder.append(/*5.12*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.13*/"0;")
            stringBuilder.append(/*5.14*/"0;")
            stringBuilder.append(/*5.15*/"0;")
            stringBuilder.append(/*5.16*/"STANDARD_RATE;")
            stringBuilder.append(/*5.17*/"1075000;")
            stringBuilder.append(/*5.18*/"BASIC_NET;")
            stringBuilder.append(/*5.19*/"RECEIPT_OF_GOODS;")
            stringBuilder.append(/*5.20*/"DAYS;")
            stringBuilder.appendLine(/*5.21*/"0;")
            /**9*/
            stringBuilder.append("9;")
            stringBuilder.append(/*9.1*/"3;")
            stringBuilder.appendLine(/*9.2*/"545;")
            /**100*/
            stringBuilder.append("100;")
            stringBuilder.append("${ranNum};")
            stringBuilder.append(";")
            stringBuilder.appendLine(";")
            /**101*/
            stringBuilder.append("101;")
            stringBuilder.append(/*101.1*/"${HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.vigenciaTimbrado!!)};")
            stringBuilder.append(/*101.2*/";")
            stringBuilder.appendLine(/*101.3*/"1;")
            /**102*/
            stringBuilder.append("102;")
            stringBuilder.append(/*102.1*/"1;")
            stringBuilder.append(/*102.2*/";")
            stringBuilder.append(/*102.3*/";")
            stringBuilder.append(/*102.4*/"1;")
            stringBuilder.appendLine(/*102.5*/";")
            /**103*/
            stringBuilder.append("103;")
            stringBuilder.append(/*103.1*/"1;")
            stringBuilder.append(/*103.2*/"2;")
            stringBuilder.append(/*103.3*/"PRY;")
            stringBuilder.append(/*103.4*/"${oinvDetails.oinvPos.tipoContribuyente/*103.4*/};")
            stringBuilder.append(/*103.5*/";")
            stringBuilder.append(/*103.6*/";")
            stringBuilder.append(/*103.7*/"${oinvDetails.oinvPos.cardName};")
            stringBuilder.append(/*103.8*/"${oinvDetails.oinvPos.cardName};")
            stringBuilder.append(/*103.9*/"${oinvDetails.oinvPos.address};")
            stringBuilder.append(/*103.10*/";")
            stringBuilder.append(/*103.11*/";")
            stringBuilder.append(/*103.12*/"${oinvDetails.oinvPos.correo};")
            stringBuilder.append(/*103.13*/"${oinvDetails.oinvPos.cardCode};")
            stringBuilder.append(/*103.14*/";")
            stringBuilder.append(/*103.15*/"1;")
            stringBuilder.append(/*103.16*/"1;")
            stringBuilder.append(/*103.17*/";")
            stringBuilder.appendLine(/*103.18*/"1;")
            /**104*/
            stringBuilder.append("104;")
            stringBuilder.append(/*104.1*/"1;")
            stringBuilder.append(/*104.2*/";")
            stringBuilder.appendLine(/*104.3*/"${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)};")
            /**112*/
            stringBuilder.append("112;")
            stringBuilder.appendLine(/*112.1*/"${oinvDetails.oinvPos.contado};")
            /**113*/
            stringBuilder.append("113;")
            stringBuilder.append(/*113.1*/"1;")
            stringBuilder.append(/*113.2*/"1;")
            stringBuilder.append(/*113.3*/"${oinvDetails.oinvPos.totalIvaIncluido/*113.3*/};")
            stringBuilder.append(/*113.4*/"PYG;")
            stringBuilder.append(/*113.5*/";")
            stringBuilder.appendLine(/*113.6*/";")
            line = 1
            /**118,
             * 119,
             * 120,
             * 128*/
            for (detail in details) {
                lines118.add("118;${line/*118.1*/};${line/*118.2*/};;;;;;;;;;;;")
                lines119.add("119;${line/*119.1*/};${detail.precioUnitIvaInclu};;")
                lines120.add("120;${line/*120.1*/};1;100;")
                lines128.add("128;${line/*128.1*/};0;;;")
                line++
            }
            stringBuilder.apply {
                appendLine(lines118.joinToString("\n"))
                appendLine(lines119.joinToString("\n"))
                appendLine(lines120.joinToString("\n"))
                appendLine(lines128.joinToString("\n"))
            }
            /**140*/
            stringBuilder.appendLine("140;0;0;0;false;")
            /**970*/
            stringBuilder.append("970;")
            stringBuilder.append("${/*970.1*/"80002754"};")
            stringBuilder.append("${/*970.2*/"0"};")
            stringBuilder.append("${/*970.3*/"2"};")
            stringBuilder.append("${/*970.4*/""};")
            stringBuilder.append("${/*970.5*/"VIMAR Y COMPAÑÍA S.A."};")
            stringBuilder.append("${/*970.6*/"VIMAR Y COMPAÑÍA S.A."};")
            stringBuilder.append("${/*970.7*/"ASUNCIÓN 227 C/ DEFENSORES DEL CHACO"};")
            stringBuilder.append("${/*970.8*/"0"};")
            stringBuilder.append("${/*970.9*/""};")
            stringBuilder.append("${/*970.10*/""};")
            stringBuilder.append("${/*970.11*/"12"};")
            stringBuilder.append("${/*970.12*/"154"};")
            stringBuilder.append("${/*970.13*/"5044"};")
            stringBuilder.append("${/*970.14*/"021505725"};")
            stringBuilder.append("${/*970.15*/"vimar.fe@yemita.com"};")
            stringBuilder.appendLine("${/*970.16*/"VIMAR Y COMPAÑÍA S.A."};")
            /**980*/
            stringBuilder.append("980;")
            stringBuilder.append("${/*980.1*/"1"};")
            stringBuilder.append("${/*980.2*/"APROBACION_DTE"};")
            stringBuilder.appendLine("${/*980.3*/"vimar.fe@yemita.com"};")
            /**999*/
            stringBuilder.append("999;")
            stringBuilder.append("${/*999.1*/"true"};")
            stringBuilder.append("${/*999.2*/"false"};")
        }
        // Retornar el string completo
        //  siediPreferences.saveTxtSiedi(stringBuilder.toString())
        Log.i("MensajeYtrack", stringBuilder.toString())

        sharedData.txtSiedi.value =  stringBuilder.toString()
        _initFacturaEvent.postValue(Event(Unit))
    }


    fun processReceivedData(data: String?, data2: String?) {
        Log.i("MensajeYtrack2", data!!)
        Log.i("MensajeYtrack2", data2!!)
        prepararImpresion(data2)
    }

    fun prepararImpresion(qr: String) {
        viewModelScope.launch {
            try {
                val listaFactura = getOinvUseCase.execute(0)
                val gson = Gson()
                val json = gson.toJson(listaFactura)
                val jsonObject = JSONObject(qr)


                imprimir(json, jsonObject.getString("qr"),jsonObject.getString("cdc"))
            } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }

    fun imprimir(json: String, qr: String,cdc:String) {

        val gson = Gson()
        val listType = object : TypeToken<List<OinvPosWithDetails>>() {}.type
        val oinvList: List<OinvPosWithDetails> = gson.fromJson(json, listType)
        val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
        decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
        val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
        // Iterar sobre la lista y acceder a los campos oinvPos y details
        oinvList.forEach { oinvDetails ->
            val details = oinvDetails.details
            val detailsLotes = oinvDetails.detailsLotes // Obtener los detalles de los lotes

            var totalFactura = 0
            var factura = "[L]\n" +
                    "[C]<u><font size='small'>VIMAR y CIA S.A</font></u>\n" +
                    "[C]<u><font size='small'>RUC: 80002754-0</font></u>\n" +
                    "[C]<u><font size='small'>Comercio al por mayor de </font></u>\n" +
                    "[C]<u><font size='small'>comestibles, excepto carnes</font></u>\n \n\n" +
                    "[L]<b>Timbrado Nro: ${oinvDetails.oinvPos.timb}</b>   \n" +
                    "[L]<b>Valida desde : ${ HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.vigenciaTimbrado!!)}</b>   \n" +
                    "[L]<b>Condicion de venta : ${if(oinvDetails.oinvPos.contado=="1"){"CONTADO"} else {"CREDITO"} } </b>   \n" +
                    "[L]<b>Moneda: Guarani</b>   \n" +
                    "[L]<b>IVA INCLUIDO</b>   \n" +
                    "[C]===============================================\n" +
                    "[L]<b>RUC: ${oinvDetails.oinvPos.licTradNum}  </b> \n" +
                    "[L]<b>Cliente: ${oinvDetails.oinvPos.cardName}  </b> \n" +
                    "[L]<b>Nro de factura: ${oinvDetails.oinvPos.numAtCard} </b>   \n" +
                    "[L]<b>Fecha factura:  ${HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)} </b>   \n" +
                    "[L]\n" +
                    "[C]=========================================\n" +
                    "[L]\n" +

                    "[L]<b>Impu</b>  <b>Cantidad</b>    <b>Precio</b>       [R]<b>Total</b>     \n"+
                    "[L]                  <b>Lote</b>                                           \n"

            // Añadir cada detalle al texto
            for (detail in details) {
                totalFactura +=  detail.quantity.toInt() * detail.priceAfterVat.toInt();
                val totalPrice = detail.quantity .toInt()* detail.priceAfterVat.toInt()
                val formattedTotalPrice = formatter.format(totalPrice)
                val formattedprice = formatter.format(detail.priceAfterVat.toInt())

                factura +="[L]<b>${detail.itemCode}-${detail.itemName}</b>        \n" +
                        "[L]<b>${detail.taxCode}</b>    <b>${detail.quantity}</b>          <b>${formattedprice}</b>  [R]<b>$formattedTotalPrice</b>\n"

                // Iterar sobre los detalles de los lotes para este item
                val lotesParaItem = detailsLotes.filter { it.itemCode == detail.itemCode }
                for (lote in lotesParaItem) {
                    factura += "[L]     ${lote.quantity}          ${lote.lote} \n"
                }
                factura +="[C]-----------------------------------------\n"
            }
            val totalInvoice = formatter.format(totalFactura)
            val fivePercentOfTotal = formatter.format(totalFactura * 0.05)   // Calcular el 5%

            factura += "[L]<font size='small'> TOTAL A PAGAR:              [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total Gravadas 10%:         [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total Gravadas 5%:          [L]<b>Gs. </b>[R]<b>$totalInvoice</b></font>\n" +
                    "[L]<font size='small'> Total Exentas%:             [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n" +
                    "[L]<font size='small'> Total IVA 10%:              [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[L]<font size='small'> Total IVA 5%:               [L]<b>Gs. </b>[R]<b>$fivePercentOfTotal</b></font>\n" +
                    "[L]<font size='small'> Total IVA:                  [L]<b>Gs. </b>[R]<b>0</b></font>\n" +
                    "[C]-----------------------------------------\n\n" +
                    "[C]<u><font size='small'>*** Gracias por su compra ***</font></u>\n\n" +
                    "[C]<u><font size='small'>ESTE DOCUMENTO ES UNA REPRESENTACION</font></u>\n" +
                    "[C]<u><font size='small'>GRAFICA DE UN DOCUMENTO ELECTRONICO</font></u>\n\n" +
                    "[C]<u><font size='small'>Informacion de interes del facturador </font></u>\n" +
                    "[C]<u><font size='small'>electronico emisor</font></u>\n\n" +
                    "[C]<u>Si su documento electronico presenta</u>\n" +
                    "[C]<u>algun error, podra solicitar la</u>\n" +
                    "[C]<u>modificacion dentro de las 72 horas</u>\n" +
                    "[C]<u>siguientes de la emision de este comprobante.</u>\n\n" +
                    "[C]-----------------------------------------\n" +
                    "[C]<u>Para actualizar sus datos personales</u>\n" +
                    "[C]<u>escriba a vimar.fe@yemita.com.py</u>\n" +
                    "[C]<u>Consulte la validez de esta factura</u>\n" +
                    "[C]<u>Electronica con el numero de DCD</u>\n" +
                    "[C]<u>impreso abajo en:</u>\n" +
                    "[L]<u>https://ekuatia.set.gov.py/consultas/</u>\n" +
                    "[L]<u>CDC:</u>\n" +
                    "[L]<u>"+cdc+"</u>\n" +
                    "[L]\n" +
                    "[C]<qrcode size='40'>" + qr + "</qrcode>";

            val servicioBluetooth= servicioBluetooth()
            servicioBluetooth.imprimir(factura)
        }
    }
}
