package com.portalgm.y_trackcomercial.util.firmadorFactura

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.util.Event
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.SharedData
import kotlin.random.Random

object firmarFactura {
    val sharedData = SharedData.getInstance()
    private val _initFacturaEvent = MutableLiveData<Event<Unit>>()
    val initFacturaEvent: LiveData<Event<Unit>> = _initFacturaEvent
    fun generarStringSiedi(oinvList: List<OinvPosWithDetails>,claseEnviar:String) {

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
            stringBuilder.append(/*1.2*/"${HoraActualUtils.convertirFormatoISO8601AFechaHora( oinvDetails.oinvPos.docDate!!)};")
            stringBuilder.append(/*1.3*/"ORIGINAL;")
            //stringBuilder.append(/*1.4*/"${oinvDetails.oinvPos.timb}-${oinvDetails.oinvPos.numAtCard!!.replace("-","")};")
            stringBuilder.append(/*1.4*/"12559861-0010010000122;")
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
                stringBuilder.append(/*2.5*/    "${
                    HoraActualUtils.convertIsoToDateSimple(
                        oinvDetails.oinvPos.docDate!!
                    )
                };"
                )
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
            stringBuilder.append(/*5.1*/"${oinvDetails.oinvPos.totalSinIva!!.toDouble().toInt()};")
            stringBuilder.append(/*5.2*/"${oinvDetails.oinvPos.totalIva!!.toDouble().toInt()};")
            stringBuilder.append(/*5.3*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.4*/"${oinvDetails.oinvPos.totalSinIva!!.toDouble().toInt()};")
            stringBuilder.append(/*5.5*/"${oinvDetails.oinvPos.totalIva!!.toDouble().toInt()};")
            stringBuilder.append(/*5.6*/"5;")
            stringBuilder.append(/*5.7*/"STANDARD_RATE;")
            stringBuilder.append(/*5.8*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.9*/"0;")
            stringBuilder.append(/*5.10*/"0;")
            stringBuilder.append(/*5.11*/"10;")
            stringBuilder.append(/*5.12*/"STANDARD_RATE;")
            stringBuilder.append(/*5.13*/"VALUE_ADDED_TAX;")
            stringBuilder.append(/*5.14*/"0;")
            stringBuilder.append(/*5.15*/"0;")
            stringBuilder.append(/*5.16*/"0;")
            stringBuilder.append(/*5.17*/"STANDARD_RATE;")
            stringBuilder.append(/*5.18*/"${oinvDetails.oinvPos.totalIvaIncluido!!.toDouble().toInt()};")
            stringBuilder.append(/*5.19*/"BASIC_NET;")
            stringBuilder.append(/*5.20*/"RECEIPT_OF_GOODS;")
            stringBuilder.append(/*5.21*/"DAYS;")
            stringBuilder.appendLine(/*5.22*/"${if(oinvDetails.oinvPos.contado.equals("1")){""}else{ extraerEnteros(if(oinvDetails.oinvPos.pymntGroup!!.equals("CREDITO"))"30" else oinvDetails.oinvPos.pymntGroup!! )}};")
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
            // stringBuilder.append(/*101.1*/"${HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.vigenciaTimbrado!!)};")
            stringBuilder.append(/*101.1*/"2022-09-19;")
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
            stringBuilder.append(/*103.1*/"${oinvDetails.oinvPos.naturalezaReceptor/*103.4*/};")//CONTRIBUYENTE O NO CONTRIBUYENTE // 1 CONTRIBUYENTE O 2 NO CONTRIBUYENTE
            stringBuilder.append(/*103.2*/"2;")
            stringBuilder.append(/*103.3*/"PRY;")
            stringBuilder.append(/*103.4*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("1")){oinvDetails.oinvPos.tipoContribuyente}else{""}/*103.4*/};") // SI ES 1 INFORMAR, SI ES 2 NO INFORMAR , SI ES 1 PONER 1-FISICA O 2-JURIDICA
            stringBuilder.append(/*103.5*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("2")){"1"}else{""}};")// SI NO ES CONTRIBUYENTE PONER 1-CEDULA PARAGUAYA O SI ES CONTRIBUYENTE DEJAR VACIO
            stringBuilder.append(/*103.6*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("2")){oinvDetails.oinvPos.licTradNum}else{""}};")// SI ES CEDULA DE IDENTIDAD PONER EL NRO DE LA CEDULA DE IDENTIDAD, SI LA FACTURA ES SIN NOMBRE ENTONCES PONER 0
            stringBuilder.append(/*103.7*/"${oinvDetails.oinvPos.cardName};")
            stringBuilder.append(/*103.8*/"${oinvDetails.oinvPos.cardName};")
            stringBuilder.append(/*103.9*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("1")){oinvDetails.oinvPos.STREET}else{""}};")// ES OBLIGATORIO SI ES CONTRIBUYENTE
            stringBuilder.append(/*103.10*/";")
            stringBuilder.append(/*103.11*/";")
            stringBuilder.append(/*103.12*/"${oinvDetails.oinvPos.correo};")
            stringBuilder.append(/*103.13*/"${oinvDetails.oinvPos.cardCode};")
            stringBuilder.append(/*103.14*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("2")){"Cédula paraguaya"}else{""}/*103.4*/};")
            stringBuilder.append(/*103.15*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("1")){oinvDetails.oinvPos.u_sifenncasa}else{""}/*103.15*/};")
            stringBuilder.append(/*103.16*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("1")){oinvDetails.oinvPos.u_deptocod}else{""}/*103.16*/};")//
            stringBuilder.append(/*103.17*/";")
            stringBuilder.appendLine(/*103.18*/"${if(oinvDetails.oinvPos.naturalezaReceptor.equals("1")){oinvDetails.oinvPos.u_sifenciudad}else{""}/*103.18*/};")
            /**104*/
            stringBuilder.append("104;")
            stringBuilder.append(/*104.1*/"1;")
            stringBuilder.append(/*104.2*/";")
            stringBuilder.appendLine(/*104.3*/"${HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)};")
            /**112*/
            stringBuilder.append("112;")
            stringBuilder.appendLine(/*112.1*/"${oinvDetails.oinvPos.contado};")
            /**113*/
            if(oinvDetails.oinvPos.contado.equals("1")){
                stringBuilder.append("113;")
                stringBuilder.append(/*113.1*/"1;")
                stringBuilder.append(/*113.2*/"1;")
                stringBuilder.append(/*113.3*/"${oinvDetails.oinvPos.totalIvaIncluido/*113.3*/};")
                stringBuilder.append(/*113.4*/"PYG;")
                stringBuilder.append(/*113.5*/";")
                stringBuilder.appendLine(/*113.6*/";")
            }
            else{
                /**116*/
                stringBuilder.append("116;")
                stringBuilder.append(/*116.1*/"1;")
                stringBuilder.append(/*116.2*/"${oinvDetails.oinvPos.pymntGroup};")
                stringBuilder.append(/*116.3*/";")
                stringBuilder.appendLine(/*116.4*/";")
            }

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
        sharedData.clase_a_enviarSiedi.value=claseEnviar
        sharedData.txtSiedi.value = stringBuilder.toString()
        Log.i("YtrackMen",  sharedData.txtSiedi.value!!)
        _initFacturaEvent.postValue(Event(Unit))
    }
}


fun extraerEnteros(cadena: String): Int {
    // Filtra solo los caracteres que son dígitos y únelos en una nueva cadena
    val soloNumeros = cadena.filter { it.isDigit() }
    // Convierte la cadena de números en un entero
    return soloNumeros.toInt()
}


