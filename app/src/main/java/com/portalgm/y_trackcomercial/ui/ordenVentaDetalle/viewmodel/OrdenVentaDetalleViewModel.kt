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
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1Detalle
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaCabItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaDetItem
import com.portalgm.y_trackcomercial.data.model.models.ventas.OrdenVentaProductosSeleccionados
import com.portalgm.y_trackcomercial.services.bluetooth.ImpresionResultado
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBluetooth
//import com.portalgm.y_trackcomercial.ui.ordenVentaDetalle.screen.ProductoItem
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.GetOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.InsertTransactionOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.UpdateFirmaOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaCabByIdUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.GetOrdenVentaDetUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetDatosDetalleLotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetDatosFacturaUseCase
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.calculosIva
import com.portalgm.y_trackcomercial.util.firmadorFactura.firmarFactura
import com.portalgm.y_trackcomercial.util.impresion.layoutFactura
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject


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
  /*  private val _initFacturaEvent = MutableLiveData<Event<Unit>>()
    val initFacturaEvent: LiveData<Event<Unit>> = _initFacturaEvent*/

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
    private val _totalIngresadoPorProducto = mutableStateOf<Map<String, Double>>(emptyMap())

    // Mapa para almacenar las cantidades ingresadas por lote
    private val _cantidadesIngresadasPorLote = mutableStateMapOf<String, Number>()
    // private val _cantidadesIngresadasPorLote = mutableStateMapOf<String, Cantidades>()

    private val _loadingPantalla = MutableLiveData<Boolean>()
    val loadingPantalla: LiveData<Boolean> = _loadingPantalla
    private val _dialogPantalla = MutableLiveData<Boolean>()
    val dialogPantalla: LiveData<Boolean> = _dialogPantalla

    private val _mensajePantalla = MutableLiveData<String>()
    val mensajePantalla: LiveData<String> = _mensajePantalla

    private val _nroFactura = MutableLiveData<String>()
    val nroFactura: LiveData<String> = _nroFactura

    val sharedData = SharedData.getInstance()

    private val _pantallaConfirmacion = MutableLiveData<Boolean>()
    val pantallaConfirmacion: LiveData<Boolean> = _pantallaConfirmacion

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
    fun mostrarConfirmacion( ){
        _pantallaConfirmacion.value=true
    }
    fun cancelar(){
        _pantallaConfirmacion.value=false
    }

    fun registrar(productos: List<OrdenVentaProductosSeleccionados>) {
        val INV1_POS_LIST = mutableListOf<INV1_POS>()
        val INV1_LOTES_POS_LIST = mutableListOf<INV1_LOTES_POS>()
        var lineNumInv1=0
        _mensajePantalla.value = "Procesando factura..."
        _loadingPantalla.value = true
        // Aquí puedes agregar lógica para procesar los productos seleccionados
        _productosSeleccionados.value = productos
        val itemCodesSeleccionados = productos.map { it.itemCode }


        var totalFactura = 0.0
        _productosSeleccionados.value!!.forEach { producto ->
            if(producto.itemCode.length==1)totalFactura += producto.quantity.toDouble() * producto.priceAfVAT
            else totalFactura += producto.quantity.toInt() * producto.priceAfVAT.toInt()
        }

        viewModelScope.launch {
            /**PREPARA CABECERA*/
            val OINV_POS = OINV_POS(
                idVisita = 0,
                docEntryPedido = _docEntryPedido.value,
                docEntry = System.currentTimeMillis(),
                lineNumbCardCode = _datosOrdenVenta.value!![0].lineNumbCardCode,
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
                estado = "S",
                condicion =  _datosOrdenVenta.value!![0].groupNum,
                pymntGroup=_datosOrdenVenta.value!![0].pymntGroup,
                docEntrySap = "-"
            )
            /**PREPARA DETALLE DE LA FACTURA*/
            _productosSeleccionados.value!!.forEach { producto ->
                val newDet = INV1_POS(
                    docEntry = 0,
                    lineNum = lineNumInv1,
                    itemCode = producto.itemCode,
                    itemName = producto.itemName,
                    whsCode = _datosFactura.value!![0].U_DEPOSITO!!,
                    quantity = producto.quantity.toString(),
                    priceAfterVat = producto.priceAfVAT.toInt().toString(),
                    precioUnitSinIva =  calculosIva.redondeoPersonalizado(calculosIva.calcularPrecioSinIva(producto.priceAfVAT)).toInt().toString(),
                    precioUnitIvaInclu = producto.priceAfVAT.toInt().toString(),
                    totalSinIva = calculosIva.calcularTotalSinIva(
                        producto.quantity.toDouble(),
                        producto.priceAfVAT
                    ).toInt().toString(),
                    totalIva = calculosIva.calcularIva(producto.quantity.toDouble(), producto.priceAfVAT).toInt().toString(),
                    uomEntry = 1,
                    taxCode = "5"
                )
                INV1_POS_LIST.add(newDet)
                lineNumInv1 += 1
            }
            /**PREPARA DETALLE DE LOS LOTES*/
            _cantidadesIngresadasPorLote.forEach { (batchNum, cantidad) ->
                if (cantidad.toDouble() > 0) {
                    val lote = lotesIncializados.value?.find { it.batchNum == batchNum && it.itemCode in itemCodesSeleccionados }

                    val itemCode = lote?.itemCode ?: ""
                   // var unitMsr =  ""
                    val unitMsr = _productosSeleccionados.value?.find { it.itemCode == itemCode }?.unitMsr ?: ""
                 /*   productos.forEach {
                        if(it.itemCode==lote!!.itemCode){
                            unitMsr=it.unitMsr
                        }
                    }*/

                    val quantityCalculado = when (unitMsr) {
                        "Docena" -> cantidad.toDouble() / 12
                        "Plancha" -> cantidad.toDouble() / 30
                        else -> cantidad.toDouble()
                    }

                    if (lote != null) {
                        val newDet = INV1_LOTES_POS(
                            docEntry = 0,
                            itemCode = itemCode,
                            quantity = cantidad.toString(),
                            quantityCalculado = calculosIva.formatearCantidad(quantityCalculado),
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
            firmarFactura.generarStringSiedi(oinvList,"1")
        }
    }

    //FUNCION PASADA AL VIEWMODEL, PARA OBTENER LOS PRODUCTOS DESDE EL INICIO Y PODER USARLO AL MOMENTO DE COMPROBACION DEL BOTON REGISTRAR
    fun getProductosSeleccionados(
        articulosMenu: List<ProductoItem>,
        selections: Map<ProductoItem, MutableState<Boolean>>,
        quantities: Map<ProductoItem, MutableState<Number>>,
        quantitiesUnidad: Map<ProductoItem, MutableState<Int>>,
     ): List<OrdenVentaProductosSeleccionados> {
        val selectedItems = articulosMenu.filter { selections[it]?.value == true }
        return selectedItems.map { item ->
            OrdenVentaProductosSeleccionados(
                itemName = item.name,
                quantity = if(item.itemCode.length==1) quantities[item]?.value?.toDouble() ?: 0.0 else quantities[item]?.value?.toInt() ?: 0  ,//quantities[item]?.value ?: 0.0,
                itemCode = item.itemCode,
                lineNumDet = item.lineNum,
                priceAfVAT = item.price,
                unitMsr = item.unitMsr,
                quantityUnidad= (quantitiesUnidad[item]?.value?.toDouble()?: 0.0).toInt()
            )
        }
    }

    fun comprobarEstadoBoton(productos: List<OrdenVentaProductosSeleccionados>) {
        val itemCodesSeleccionados = productos.map { it.itemCode }
        if (itemCodesSeleccionados.isNullOrEmpty()) {
            _mostrarBotonRegistrar.value = false
            _pantallaConfirmacion.value = false

        } else {
            // Verificar si la cantidad sugerida es igual a la cantidad ingresada para todos los productos seleccionados
            val cantidadSugeridaIgualCantidadIngresada = productos.all { producto ->
                _totalIngresadoPorProducto.value[producto.itemCode] == producto.quantityUnidad.toDouble()
            }
            _mostrarBotonRegistrar.value = cantidadSugeridaIgualCantidadIngresada
            _pantallaConfirmacion.value = false
        }
    }
    fun actualizarCantidadIngresada(batchNum: String, itemCode: String, nuevaCantidad: Number) {
        // Convertir la cantidad previa y nueva a Double para cálculos consistentes
        val cantidadPrevia = _cantidadesIngresadasPorLote.getOrDefault(batchNum, 0.0).toDouble()
        val nuevaCantidadDouble = nuevaCantidad.toInt()
        // Actualizar con la nueva cantidad ingresada
        _cantidadesIngresadasPorLote[batchNum] = nuevaCantidadDouble
        // Calcular la diferencia de cantidades
        val diferencia = nuevaCantidadDouble - cantidadPrevia
        // Actualizar el total de cantidades por producto basado en la diferencia calculada
        val cantidadesActuales = _totalIngresadoPorProducto.value.toMutableMap()
        val cantidadActual = cantidadesActuales[itemCode] ?: 0.0
        val cantidadActualizada = (cantidadActual + diferencia).coerceAtLeast(0.0)  // Asegura que la cantidad no sea negativa

        // Guardar la cantidad actualizada
        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales
        // Llamar a la función para comprobar el estado del botón
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }
 /*   fun actualizarCantidadIngresada(batchNum: String, itemCode: String, nuevaCantidad: Double) {
        val esDouble = itemCode.length == 1 // Determinar si necesita manejarlo como Double
        val cantidadPrevia: Double = if (esDouble) {
            _cantidadesIngresadasPorLoteDouble.getOrDefault(batchNum, 0.0)
        } else {
            _cantidadesIngresadasPorLote.getOrDefault(batchNum, 0).toDouble()
        }
        val nuevaCantidadDouble = nuevaCantidad.toDouble()
        val diferencia = nuevaCantidadDouble - cantidadPrevia

        val cantidadesActuales = _totalIngresadoPorProducto.value.toMutableMap()
        val cantidadActual = cantidadesActuales[itemCode] ?: 0.0
        val cantidadActualizada = (cantidadActual + diferencia).coerceAtLeast(0.0)

        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales

      /*  if (esDouble) {
            _cantidadesIngresadasPorLoteDouble[batchNum] = nuevaCantidadDouble
        } else {*/
            _cantidadesIngresadasPorLote[batchNum] = nuevaCantidad
       // }
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }


    fun actualizarCantidadIngresada(batchNum: String, itemCode: String, nuevaCantidad: Double) {
        // Obtener la cantidad previamente ingresada para este lote, si no hay ninguna, asume cero
        val cantidadPrevia = _cantidadesIngresadasPorLote.getOrDefault(batchNum, 0.0)
        // Actualizar con la nueva cantidad ingresada
        _cantidadesIngresadasPorLote[batchNum] = nuevaCantidad
        // Calcular la diferencia de cantidades
        val diferencia = nuevaCantidad - cantidadPrevia
        // Actualizar el total de cantidades por producto basado en la diferencia calculada
        val cantidadesActuales = _totalIngresadoPorProducto.value.toMutableMap()
        val cantidadActual = cantidadesActuales[itemCode] ?: 0.0
        val cantidadActualizada =
            (cantidadActual + diferencia).coerceAtLeast(0.0) // Asegura que la cantidad no sea negativa

        // Guardar la cantidad actualizada
        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales

        // Llamar a la función para comprobar el estado del botón
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }*//*
    fun actualizarCantidadIngresadaDouble(batchNum: String, itemCode: String, nuevaCantidad: Int) {
        // Obtener la cantidad previamente ingresada para este lote, si no hay ninguna, asume cero
        val cantidadPrevia = _cantidadesIngresadasPorLoteDouble.getOrDefault(batchNum, 0.0)
        // Actualizar con la nueva cantidad ingresada
        _cantidadesIngresadasPorLoteDouble[batchNum] = nuevaCantidad.toDouble()
        // Calcular la diferencia de cantidades
        val diferencia = nuevaCantidad - cantidadPrevia
        // Actualizar el total de cantidades por producto basado en la diferencia calculada
        val cantidadesActuales = _totalIngresadoPorProducto.value.toMutableMap()
        val cantidadActual = cantidadesActuales[itemCode] ?: 0.0
        val cantidadActualizada =
            (cantidadActual + diferencia).coerceAtLeast(0.0) // Asegura que la cantidad no sea negativa

        // Guardar la cantidad actualizada
        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales

        // Llamar a la función para comprobar el estado del botón
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }*/
    fun inicializarProductosSeleccionados(productos: List<OrdenVentaProductosSeleccionados>) {
        _productosSeleccionados.value = productos
    }

    // Obtener la suma de cantidades cargadas por itemCode
    fun getCantidadCargadaPorItemCode(itemCode: String): Double {
        val suma = _totalIngresadoPorProducto.value.getOrDefault(itemCode, 0.0)
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
        _nroFactura.value=(_datosFactura.value!![0].ult_nro_fact.toInt()+1).toString()
        _docNum.value = docNum
    }

    private fun convertToProductoItems(ordenVentaDetItems: List<OrdenVentaDetItem>): List<ProductoItem> {
        return ordenVentaDetItems.map {
            ProductoItem(
                lineNum = it.lineNumDet,
                name = it.itemName,
                itemCode = it.itemCode, // Usando itemCode como descripción por ejemplo
                price = it.priceAfVAT.toDouble(),
                initialQuantity = it.quantity.toDouble(),
                unitMsr = it.unitMsr,
                quantityUnidad=it.quantityUnidad
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

                    else -> {}
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

        if (datosQrCdc.equals("null")) {
            _mensajePantalla.value = "Ha ocurrido en error al firmar \n$datosXml"
            _loadingPantalla.value = false
            _dialogPantalla.value = true
        } else {
            prepararImpresion(datosQrCdc!!, datosXml!!)

        }
    }

}
data class ProductoItem(
    val lineNum: Int,
    val name: String,
    val itemCode: String,
    val price: Double,
    val initialQuantity: Number,
    val quantityUnidad: Int,
    val unitMsr: String
)
