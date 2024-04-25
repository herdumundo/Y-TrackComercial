package com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1Detalle
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1LoteDetalle
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaDetItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaProductosSeleccionados
import com.portalgm.y_trackcomercial.services.bluetooth.ImpresionResultado
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBluetooth
import com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.screen.ProductoItem
import com.portalgm.y_trackcomercial.usecases.ventas.inv1.InsertInv1UseCase
import com.portalgm.y_trackcomercial.usecases.ventas.inv1Lotes.InsertInv1LotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.InsertOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.InsertTransactionOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.UpdateFirmaOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaCabByIdUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaDetUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetDatosDetalleLotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetDatosFacturaUseCase
import com.portalgm.y_trackcomercial.util.Event
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.impresion.layoutFactura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class OrdenVentaDetalleViewModel @Inject constructor(
    private val getOrdenVentaDetUseCase: GetOrdenVentaDetUseCase,
    private val getDatosFacturaUseCase: GetDatosFacturaUseCase,
    private val getOrdenVentaCabByIdUseCase: GetOrdenVentaCabByIdUseCase,
    private val getOinvUseCase: GetOinvUseCase,
    private val getDatosDetalleLotesUseCase: GetDatosDetalleLotesUseCase,
    private val insertTransactionOinvUseCase: InsertTransactionOinvUseCase, // Inyecta la instancia de la base de datos
    private val updateFirmaOinvUseCase: UpdateFirmaOinvUseCase, // Inyecta la instancia de la base de datos

) : ViewModel() {
    private val _initFacturaEvent = MutableLiveData<Event<Unit>>()
    val initFacturaEvent: LiveData<Event<Unit>> = _initFacturaEvent

    private val _productos = MutableLiveData<List<ProductoItem>>()
    val productos: LiveData<List<ProductoItem>> = _productos
    private val _datosOrdenVenta = MutableLiveData<List<OrdenVentaCabItem>>()
    private val _productosSeleccionados = MutableLiveData<List<OrdenVentaProductosSeleccionados>>()
    private val _lotesIncializados = MutableLiveData<List<DatosDetalleLotes>>()
    val lotesIncializados: LiveData<List<DatosDetalleLotes>> = _lotesIncializados
    private val _datosFactura = MutableLiveData<List<A0_YTV_VENDEDOR_Entity>>()
    private val _docNum = MutableLiveData<Int>()//idOrdenVenta
    val docNum: LiveData<Int> = _docNum
    val Inv1DetalleList = mutableStateListOf<Inv1Detalle>()
    private val _itemCodes = MutableLiveData<List<String>>()
    private val _docEntryGenerado = MutableLiveData<Long>()
    private val _docEntryPedido = MutableLiveData<String>()
    private val cantidadesPorLote = mutableMapOf<String, Int>()

    // LiveData para la cantidad total cargada
    private val _cantidadCargadaTotal = MutableLiveData<Int>()
    private val _mostrarBotonRegistrar = MutableLiveData<Boolean>()
    val mostrarBotonRegistrar: LiveData<Boolean> = _mostrarBotonRegistrar
    private val _totalIngresadoPorProducto = mutableStateOf<Map<String, Int>>(emptyMap())

    // Mapa para almacenar las cantidades ingresadas por lote
    private val _cantidadesIngresadasPorLote = mutableStateMapOf<String, Int>()
    private val _loadingPantalla = MutableLiveData<Boolean>()
    val loadingPantalla: LiveData<Boolean> = _loadingPantalla
    private val _dialogPantalla = MutableLiveData<Boolean>()
    val dialogPantalla: LiveData<Boolean> = _dialogPantalla

    private val _mensajePantalla = MutableLiveData<String>()
    val mensajePantalla: LiveData<String> = _mensajePantalla


    val sharedData = SharedData.getInstance()

    fun inicializarDatos(docNum: Int) {
        _docEntryPedido.value = docNum.toString()
        _mostrarBotonRegistrar.value = false
        viewModelScope.launch {
            try {
                cargarProductosOrden(docNum)
                cargarLotesProductos()
                cargarDatosAdicionales(docNum)
            } catch (e: Exception) {
                // _error.value = e.message
            } finally {
                //      _isLoading.value = false
            }
        }
    }

    fun registrar(productos: List<OrdenVentaProductosSeleccionados>) {
        _mensajePantalla.value = "Procesando factura..."
        _loadingPantalla.value = true
        // Aquí puedes agregar lógica para procesar los productos seleccionados
        _productosSeleccionados.value = productos
        val itemCodesSeleccionados = productos.map { it.itemCode }

        /*   val itemCodesPedido = _itemCodes.value ?: emptyList()
         val itemCodesSeleccionados = productos.map { it.itemCode }
         Log.d("MensajeYtrackTotalIngresadoPorProducto", _totalIngresadoPorProducto.value.toString())
         val productosSeleccionados = _productosSeleccionados.value ?: emptyList()
         productosSeleccionados.forEach { producto ->
             Log.d("MensajeYtrackCantidadesPorProducto", producto.itemCode +" Cantidad:"+ producto.quantity )
         }
          val sonIguales = if (itemCodesSeleccionados.isNullOrEmpty()) {
              Log.d("MensajeYtrack", "Esta vacio")
             false
         } else {
              _mostrarBotonRegistrar.value=true

              // Si itemCodesSeleccionados tiene elementos
             itemCodesPedido == itemCodesSeleccionados
         }
         if (sonIguales) {
             Log.d("MensajeYtrack", "Son iguales")
         } else {
             Log.d("MensajeYtrack", "NO son iguales")
         }
          _cantidadesIngresadasPorLote.forEach { (batchNum, cantidad) ->
             if(cantidad>0) {
                 Log.d("MensajeYtrackItemCodeSeleccionados", "ItemCode: $itemCodesSeleccionados")

                 val lote = lotesIncializados.value?.find { it.batchNum == batchNum  && it.itemCode in itemCodesSeleccionados  }
                 val itemCode = lote?.itemCode ?: ""
                 if (cantidad > 0 && lote != null) {
                     Log.d("MensajeYtrackCantidadPorLotes", "ItemCode: $itemCode - Lote: $batchNum - Cantidad: $cantidad")
                 }
             }
         }*/
        val INV1_POS_LIST = mutableListOf<INV1_POS>()
        val INV1_LOTES_POS_LIST = mutableListOf<INV1_LOTES_POS>()

        var totalFactura = 0
        _productosSeleccionados.value!!.forEach { producto ->
            totalFactura += producto.quantity * producto.priceAfVAT
        }

        viewModelScope.launch {
            /**PREPARA CABECERA*/
            val OINV_POS = OINV_POS(
                idVisita = 0,
                docEntryPedido = _docEntryPedido.value,
                docEntry = System.currentTimeMillis(),
                cardCode = _datosOrdenVenta.value!![0].cardCode,
                licTradNum = _datosOrdenVenta.value!![0].licTradNum,
                cardName = _datosOrdenVenta.value!![0].shipToCode,
                docDate = HoraActualUtils.obtenerFechaHoraActual(),
                docDueDate = _datosOrdenVenta.value!![0].docDueDate,
                series = _datosFactura.value!![0].U_SERIEFACT.toString(),
                folioNumber = _datosFactura.value!![0].ult_nro_fact.toInt(),
                numAtCard = _datosFactura.value!![0].u_esta + "-" + _datosFactura.value!![0].u_pemi + "-" + _datosFactura.value!![0].ult_nro_fact,
                slpCode = _datosFactura.value!![0].slpcode,
                timb = _datosFactura.value!![0].U_TimbradoNro.toString(),
                cdc = "",
                qr = "",
                xmlFact = "",
                xmlNombre = "",
                iva = "5",
                vigenciaTimbrado = _datosFactura.value!![0].U_fecha_autoriz_timb.toString(),
                tipoContribuyente = "1",
                ci = "0",
                address = _datosOrdenVenta.value!![0].shipToCode,
                correo = "prueba@hotmail.com",
                contado = if (_datosOrdenVenta.value!![0].pymntGroup == "Contado") {
                    "1"
                } else {
                    "2"
                }, // 1 si es contado 2 si es credito
                totalIvaIncluido = totalFactura.toString(),
                estado = "P"
            )
            /**PREPARA DETALLE DE LA FACTURA*/
            _productosSeleccionados.value!!.forEach { producto ->
                val newDet = INV1_POS(
                    docEntry = 0,
                    lineNum = producto.lineNumDet,
                    itemCode = producto.itemCode,
                    itemName = producto.itemName,
                    whsCode = _datosFactura.value!![0].U_DEPOSITO!!,
                    quantity = producto.quantity.toString(),
                    priceAfterVat = producto.priceAfVAT.toString(),
                    precioUnitSinIva = calcularPrecioSinIva(producto.priceAfVAT).toString(),
                    precioUnitIvaInclu = producto.priceAfVAT.toString(),
                    totalSinIva = calcularTotalSinIva(
                        producto.quantity,
                        producto.priceAfVAT
                    ).toString(),
                    totalIva = calcularIva(producto.quantity, producto.priceAfVAT).toString(),
                    uomEntry = 1,
                    taxCode = "5"
                )
                INV1_POS_LIST.add(newDet)
            }
            /**PREPARA DETALLE DE LOS LOTES*/
            _cantidadesIngresadasPorLote.forEach { (batchNum, cantidad) ->
                if (cantidad > 0) {
                    val lote =
                        lotesIncializados.value?.find { it.batchNum == batchNum && it.itemCode in itemCodesSeleccionados }
                    val itemCode = lote?.itemCode ?: ""
                    if (lote != null) {
                        val newDet = INV1_LOTES_POS(
                            docEntry = 0,
                            itemCode = itemCode,
                            quantity = cantidad.toString(),
                            lote = batchNum,
                        )
                        INV1_LOTES_POS_LIST.add(newDet)
                    }
                }
            }
            /**MANDA DATOS DE LOS TRES CUERPOS PARA INSERTARLOS*/
            val docEntry = insertTransactionOinvUseCase.Insertar(
                OINV_POS,
                INV1_POS_LIST,
                INV1_LOTES_POS_LIST,
                _docNum.value.toString()
            )
            _docEntryGenerado.value = docEntry
            // Obtener los datos de la base de datos
            val oinvList = withContext(Dispatchers.IO) {
                getOinvUseCase.execute(docEntry)
            }
            generarStringSiedi(oinvList)
        }
    }

    //FUNCION PASADA AL VIEWMODEL, PARA OBTENER LOS PRODUCTOS DESDE EL INICIO Y PODER USARLO AL MOMENTO DE COMPROBACION DEL BOTON REGISTRAR
    fun getProductosSeleccionados(
        articulosMenu: List<ProductoItem>,
        selections: Map<ProductoItem, MutableState<Boolean>>,
        quantities: Map<ProductoItem, MutableState<Int>>
    ): List<OrdenVentaProductosSeleccionados> {
        val selectedItems = articulosMenu.filter { selections[it]?.value == true }
        return selectedItems.map { item ->
            OrdenVentaProductosSeleccionados(
                itemName = item.name,
                quantity = quantities[item]?.value ?: 0,
                itemCode = item.itemCode,
                lineNumDet = item.lineNum,
                priceAfVAT = item.price
            )
        }
    }


    fun comprobarEstadoBoton(productos: List<OrdenVentaProductosSeleccionados>) {
        val itemCodesSeleccionados = productos.map { it.itemCode }
        if (itemCodesSeleccionados.isNullOrEmpty()) {
            _mostrarBotonRegistrar.value = false
        } else {
            // Verificar si la cantidad sugerida es igual a la cantidad ingresada para todos los productos seleccionados
            val cantidadSugeridaIgualCantidadIngresada = productos.all { producto ->
                _totalIngresadoPorProducto.value[producto.itemCode] == producto.quantity
            }
            _mostrarBotonRegistrar.value = cantidadSugeridaIgualCantidadIngresada
        }
    }

    fun actualizarCantidadIngresada(batchNum: String, itemCode: String, nuevaCantidad: Int) {
        // Obtener la cantidad previamente ingresada para este lote, si no hay ninguna, asume cero
        val cantidadPrevia = _cantidadesIngresadasPorLote.getOrDefault(batchNum, 0)
        // Actualizar con la nueva cantidad ingresada
        _cantidadesIngresadasPorLote[batchNum] = nuevaCantidad
        // Calcular la diferencia de cantidades
        val diferencia = nuevaCantidad - cantidadPrevia
        // Actualizar el total de cantidades por producto basado en la diferencia calculada
        val cantidadesActuales = _totalIngresadoPorProducto.value.toMutableMap()
        val cantidadActual = cantidadesActuales[itemCode] ?: 0
        val cantidadActualizada =
            (cantidadActual + diferencia).coerceAtLeast(0) // Asegura que la cantidad no sea negativa

        // Guardar la cantidad actualizada
        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales

        // Llamar a la función para comprobar el estado del botón
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }

    fun inicializarProductosSeleccionados(productos: List<OrdenVentaProductosSeleccionados>) {
        _productosSeleccionados.value = productos
    }

    // Obtener la suma de cantidades cargadas por itemCode
    fun getCantidadCargadaPorItemCode(itemCode: String): Int {
        val suma = _totalIngresadoPorProducto.value.getOrDefault(itemCode, 0)
        Log.d("OrdenVentaDetalleViewModel", "Suma de cantidades para $itemCode: $suma")
        return suma
    }

    // Función para obtener la cantidad ingresada para un lote específico
    fun getCantidadIngresadaParaLote(batchNum: String): String {
        return _cantidadesIngresadasPorLote[batchNum].toString()
    }

    private suspend fun cargarProductosOrden(docNum: Int) {
        val listaProductosOrden = getOrdenVentaDetUseCase.Obtener(docNum)
        _productos.value = convertToProductoItems(listaProductosOrden)
        val itemCodes = listaProductosOrden.map { it.itemCode }.distinct()
        _itemCodes.value = itemCodes // Suponiendo que tienes una LiveData para itemCodes
    }

    private suspend fun cargarLotesProductos() {
        val itemCodes = _itemCodes.value ?: return
        val listaLotesPorProductos = getDatosDetalleLotesUseCase.Obtener(itemCodes)
        _lotesIncializados.value = listaLotesPorProductos
    }

    private suspend fun cargarDatosAdicionales(docNum: Int) {
        _datosOrdenVenta.value = getOrdenVentaCabByIdUseCase.Obtener(docNum)
        _datosFactura.value = getDatosFacturaUseCase.Obtener()
        _docNum.value = docNum
    }

    private fun convertToProductoItems(ordenVentaDetItems: List<OrdenVentaDetItem>): List<ProductoItem> {
        return ordenVentaDetItems.map {
            ProductoItem(
                lineNum = it.lineNumDet,
                name = it.itemName,
                itemCode = it.itemCode, // Usando itemCode como descripción por ejemplo
                price = it.priceAfVAT.toInt(),
                initialQuantity = it.quantity.toInt()
            )
        }
    }

    fun limpiarLista() {
        _productos.value = emptyList()
        _datosOrdenVenta.value = emptyList()
        _productosSeleccionados.value = emptyList()
        _lotesIncializados.value = emptyList()
        _datosFactura.value = emptyList()
        _itemCodes.value = emptyList()
        _totalIngresadoPorProducto.value = emptyMap()
        _cantidadesIngresadasPorLote.clear()
        cantidadesPorLote.clear()
        Inv1DetalleList.clear()
        _docNum.value = null
        _cantidadCargadaTotal.value = null
        _dialogPantalla.value = false
    }

    fun calcularPrecioSinIva(priceWithVat: Int): Int {
        return (priceWithVat / 1.05).toInt()
    }

    fun calcularTotalSinIva(quantity: Int, priceWithVat: Int): Int {
        val priceWithoutVat = calcularPrecioSinIva(priceWithVat)
        return quantity * priceWithoutVat
    }

    fun calcularIva(quantity: Int, priceWithVat: Int): Int {
        val totalSinIva = calcularTotalSinIva(quantity, priceWithVat)
        return (totalSinIva * 0.2).toInt()
    }

    /**
     * IMPRESION Y FIRMADOR*/
   suspend fun imprimir(json: List<OinvPosWithDetails>, qr: String, cdc: String) {
        // Asegúrate de que las operaciones de impresión se realicen en el contexto de I/O
        withContext(Dispatchers.IO) {
            val servicioBluetooth = servicioBluetooth()
            val resultadoImpresion = servicioBluetooth.imprimir(layoutFactura().layoutFactura(json, qr, cdc))

            // Regresa al hilo principal para actualizar la UI
            withContext(Dispatchers.Main) {
                when (resultadoImpresion) {
                    is ImpresionResultado.Exito -> {
                        val mensajeExito = resultadoImpresion.mensaje
                        // Manejar el mensaje de éxito, por ejemplo, mostrarlo en un toast o en un log
                        _mensajePantalla.value = mensajeExito
                        _loadingPantalla.value = false
                        _dialogPantalla.value = true
                    }
                    is ImpresionResultado.Error -> {
                        val mensajeError = resultadoImpresion.mensaje
                        // Manejar el mensaje de error, por ejemplo, mostrar un mensaje al usuario indicando el error
                        _mensajePantalla.value = mensajeError
                        _loadingPantalla.value = false
                        _dialogPantalla.value = true
                    }
                }
            }
        }

    }

    fun prepararImpresion(qr: String, xml: String) {
        viewModelScope.launch {
            try {
                val listaFactura = getOinvUseCase.execute(_docEntryGenerado.value!!)
                val jsonObject = JSONObject(qr)
                updateFirmaOinvUseCase.Update(
                    jsonObject.getString("qr"),
                    xml,
                    jsonObject.getString("cdc"),
                    _docEntryGenerado.value!!
                )
                imprimir(listaFactura, jsonObject.getString("qr"), jsonObject.getString("cdc"))
            } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }

    fun processReceivedData(datosXml: String?, datosQrCdc: String?) {
        /*  Log.d("MensajeTest",data!!)
          Log.d("MensajeTest2",data2!!)*/

        if (datosQrCdc.equals("null")) {
            _mensajePantalla.value = "Ha ocurrido en error al firmar \n$datosXml"
            _loadingPantalla.value = false
            _dialogPantalla.value = true
        } else {
            prepararImpresion(datosQrCdc!!, datosXml!!)

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
            stringBuilder.append(/*1.2*/"${
                HoraActualUtils.convertirFormatoISO8601AFechaHora(
                    oinvDetails.oinvPos.docDate!!
                )
            };"
            )
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
            stringBuilder.appendLine(/*104.3*/"${HoraActualUtils.convertIsoToDateSimple(oinvDetails.oinvPos.docDate!!)};")
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
        sharedData.txtSiedi.value = stringBuilder.toString()
        _initFacturaEvent.postValue(Event(Unit))
    }
}
