package com.portalgm.y_trackcomercial.ui.repartoLibre.viewmodel


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgm.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgm.y_trackcomercial.data.model.models.OinvPosWithDetails
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosDetalleLotes
import com.portalgm.y_trackcomercial.data.model.models.ventas.DatosItemCodesStockPriceList
import com.portalgm.y_trackcomercial.data.model.models.ventas.Inv1Detalle
import com.portalgm.y_trackcomercial.repository.registroRepositories.VisitasRepository
import com.portalgm.y_trackcomercial.services.bluetooth.ImpresionResultado
import com.portalgm.y_trackcomercial.services.bluetooth.servicioBluetooth
import com.portalgm.y_trackcomercial.usecases.ocrd.GetDatosClienteUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.queries.GetOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.actions.InsertTransactionOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.oinv.actions.UpdateFirmaOinvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.CountExistenciaStockPlanchaByItemCodeUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetDatosDetalleLotesUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetItemUmedidaCambioUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.GetStockItemPriceListCodeUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetDatosFacturaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.GetUltimoNroFacturaAzureUseCase
import com.portalgm.y_trackcomercial.util.HoraActualUtils
import com.portalgm.y_trackcomercial.util.SharedData
import com.portalgm.y_trackcomercial.util.calculosIva
import com.portalgm.y_trackcomercial.util.firmadorFactura.firmarFactura
import com.portalgm.y_trackcomercial.util.impresion.layoutFactura
import com.portalgm.y_trackcomercial.util.registrosVentas.funcionesFacturas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class RepartoLibreViewModel @Inject constructor(
    private val getStockItemPriceListCodeUseCase: GetStockItemPriceListCodeUseCase,
    private val getDatosClienteUseCase: GetDatosClienteUseCase,
    private val visitasRepository: VisitasRepository,

    private val getDatosDetalleLotesUseCase: GetDatosDetalleLotesUseCase,
    private val countExistenciaStockPlanchaByItemCodeUseCase: CountExistenciaStockPlanchaByItemCodeUseCase,
    private val getItemUmedidaCambioUseCase: GetItemUmedidaCambioUseCase,
    private val getDatosFacturaUseCase: GetDatosFacturaUseCase,
    private val getOinvUseCase: GetOinvUseCase,
    private val insertTransactionOinvUseCase: InsertTransactionOinvUseCase, // Inyecta la instancia de la base de datos
    private val updateFirmaOinvUseCase: UpdateFirmaOinvUseCase, // Inyecta la instancia de la base de datos
    private val getUltimoNroFacturaAzureUseCase: GetUltimoNroFacturaAzureUseCase, // Inyecta la instancia de la base de datos

) : ViewModel() {

    private val _productos = MutableLiveData<List<ProductoItem>>()
    val productos: LiveData<List<ProductoItem>> = _productos
    private val _productosSeleccionados = MutableLiveData<List<ProductosSeleccionados>>()
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

    private val _ruc = MutableLiveData<String>()

    private var _datosCliente = MutableLiveData<List<OCRDEntity>>()
    var datosCliente: LiveData<List<OCRDEntity>> = _datosCliente

    private var _pymntGroup = MutableLiveData<String>()
    var pymntGroup: LiveData<String> = _pymntGroup
    val registrosConPendiente: LiveData<Int> = visitasRepository.getCantidadRegistrosPendientes()

    private var _groupNum = MutableLiveData<Int>()
    fun cambiarTipoPago() {
        var codCondicion = _datosCliente.value!![0].GroupNum
        if (codCondicion!!.toInt() > -1) {
            if (_groupNum.value == -1) {
                _groupNum.value = _datosCliente.value!![0].GroupNum
                _pymntGroup.value = _datosCliente.value!![0].PymntGroup
            } else {
                _groupNum.value = -1
                _pymntGroup.value = "Contado"
            }
        }
    }

    fun inicializarDatos() {


        _mostrarBotonRegistrar.value = false
        viewModelScope.launch {
            try {
                _datosCliente.value = getDatosClienteUseCase.getDatosCliente()
                cargarProductosOrden()
                cargarLotesProductos()
                cargarDatosAdicionales()
                _groupNum.value = _datosCliente.value!![0].GroupNum
                _pymntGroup.value = _datosCliente.value!![0].PymntGroup

            } catch (e: Exception) {
                // _error.value = e.message
            } finally {
                //      _isLoading.value = false
            }
        }
    }

    fun registrar(productos: List<ProductosSeleccionados>) {
        val INV1_POS_LIST = mutableListOf<INV1_POS>()
        val INV1_LOTES_POS_LIST = mutableListOf<INV1_LOTES_POS>()
        var lineNumInv1 = 0
        _mensajePantalla.value = "Procesando factura..."
        _loadingPantalla.value = true
        // Aquí puedes agregar lógica para procesar los productos seleccionados
        _productosSeleccionados.value = productos
        val itemCodesSeleccionados = productos.map { it.itemCode }


        var totalFactura = 0.0
        _productosSeleccionados.value!!.forEach { producto ->
            if (producto.itemCode.length == 1) totalFactura += producto.quantity.toDouble() * producto.priceAfVAT
            else totalFactura += producto.quantity.toInt() * producto.priceAfVAT.toInt()
        }

        viewModelScope.launch {
            _mensajePantalla.value = "Preparando nro de factura..."

            val datosAzure =
                getUltimoNroFacturaAzureUseCase.Obtener(_datosCliente.value!![0].cardCode2!!)
            val nroFactura =
                datosAzure.ult_nro_fact//LA SUMA DEL NRO SIGUIENTE SE HACE DENTRO DE LA FUNCION OBTENER.
            val limiteCredito = datosAzure.creditLine

            val balance = datosAzure.Balance + totalFactura.toLong()
            val creditDisp = datosAzure.CreditDisp - totalFactura.toLong()

            val numeroFacturaConCeros = String.format("%07d", nroFactura)
            val decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
            decimalFormatSymbols.groupingSeparator = '.' // Punto como separador de miles
            val formatter = DecimalFormat("#,##0", decimalFormatSymbols)
            val licTradNum = _datosCliente.value!![0].LicTradNum!!
            var naturalezaReceptor = funcionesFacturas.determinarNaturalezaReceptor(licTradNum)
            var tipoContribuyente = funcionesFacturas.determinarTipoContribuyente(licTradNum)

            if (_pymntGroup.value != "Contado") {
                if (balance > limiteCredito) {
                    _mensajePantalla.value = "Limite de crédito excedido \n\n" +
                            " Línea de crédito: ${formatter.format(limiteCredito)} Gs.\n" +
                            " Saldo disponible:  ${
                                formatter.format(
                                    creditDisp
                                )
                            } Gs. \n Importe a facturar  ${formatter.format(totalFactura)}  Gs. "
                    _loadingPantalla.value = false
                    _pantallaConfirmacion.value = false
                    _dialogPantalla.value = true
                    return@launch
                }
            }
            _mensajePantalla.value = "Preparando insercion cabecera..."
            val totalIva=calculosIva.calcularIva5(totalFactura.toInt())

            /**PREPARA CABECERA*/
            val OINV_POS = OINV_POS(
                idVisita = 0,
                docEntryPedido = "",//
                docEntry = System.currentTimeMillis(),
                lineNumbCardCode = _datosCliente.value!![0].LineNum!!,
                cardCode = _datosCliente.value!![0].cardCode2!!,
                licTradNum = _datosCliente.value!![0].LicTradNum!!,
                cardName = _datosCliente.value!![0].Address,
                docDate = HoraActualUtils.obtenerFechaHoraActual(),
                docDueDate = HoraActualUtils.obtenerFechaHoraActual(),
                series = _datosFactura.value!![0].U_SERIEFACT.toString(),
                folioNumber = nroFactura,
                numAtCard = _datosFactura.value!![0].u_esta + "-" + _datosFactura.value!![0].u_pemi + "-" + numeroFacturaConCeros,
                slpCode = _datosFactura.value!![0].slpcode,
                timb = _datosFactura.value!![0].U_TimbradoNro.toString(),
                cdc = "",
                qr = "",
                xmlFact = "",
                xmlNombre = "",
                iva = "5",
                vigenciaTimbrado = _datosFactura.value!![0].U_fecha_autoriz_timb.toString(),
                naturalezaReceptor = naturalezaReceptor.toString(),
                tipoContribuyente = tipoContribuyente.toString(),
                ci = "0",
                address = _datosCliente.value!![0].Address,
                correo = _datosCliente.value!![0].correo,
                contado = if (_pymntGroup.value == "Contado") {  "1"  } else {  "2" }, // 1 si es contado 2 si es credito
                totalIvaIncluido = totalFactura.toString(),
                estado = "S",
                condicion = _groupNum.value.toString(),//_datosOrdenVenta.value!![0].groupNum,
                pymntGroup = _pymntGroup.value,//_datosOrdenVenta.value!![0].pymntGroup,
                docEntrySap = "-",
                u_sifenciudad = _datosCliente.value!![0].U_SIFENCIUDAD,
                u_sifenncasa = _datosCliente.value!![0].U_SIFENNCASA,
                u_deptocod = _datosCliente.value!![0].U_DEPTOCOD,
                txtSifen = "",
                totalIva = totalIva.toString(),
                totalSinIva = (totalFactura-totalIva).toString(),
                STREET = _datosCliente.value!![0].STREET
            )
            _mensajePantalla.value = "Preparando insercion detalle..."
            /**PREPARA DETALLE DE LA FACTURA*/
            _productosSeleccionados.value!!.forEach { producto ->
                val totalIva =
                    calculosIva.calcularIva(producto.quantity.toDouble(), producto.priceAfVAT)
                        .toInt().toString()
                val totalSinIva = calculosIva.calcularTotalSinIva(
                    producto.quantity.toDouble(),
                    producto.priceAfVAT
                ).toInt().toString()
                val precioUnitSinIva =
                    calculosIva.redondeoPersonalizado(calculosIva.calcularPrecioSinIva(producto.priceAfVAT))
                        .toInt().toString()
                val newDet = INV1_POS(
                    docEntry = 0,
                    lineNum = lineNumInv1,
                    itemCode = producto.itemCode,
                    itemName = producto.itemName,
                    whsCode = _datosFactura.value!![0].U_DEPOSITO!!,
                    quantity = producto.quantity.toString(),
                    priceAfterVat = producto.priceAfVAT.toInt().toString(),
                    precioUnitSinIva = precioUnitSinIva,
                    precioUnitIvaInclu = producto.priceAfVAT.toInt().toString(),
                    totalSinIva = totalSinIva,
                    totalIva = totalIva,
                    uomEntry = 1,
                    taxCode = "5"
                )
                INV1_POS_LIST.add(newDet)
                lineNumInv1 += 1
            }
            _mensajePantalla.value = "Preparando insercion detalle lotes..."

            /**PREPARA DETALLE DE LOS LOTES*/
            _cantidadesIngresadasPorLote.forEach { (distNumber, cantidad) ->
                if (cantidad.toDouble() > 0) {
                    val lote =  lotesIncializados.value?.find { it.loteLargo == distNumber && it.itemCode in itemCodesSeleccionados }
                    val itemCode = lote?.itemCode ?: ""
                    val unitMsr = _productosSeleccionados.value?.find { it.itemCode == itemCode }?.unitMsr ?: ""
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
                            lote = lote.distNumber,
                            loteLargo = lote.loteLargo
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
                _docNum.value.toString(),
                numeroFacturaConCeros
            )
            _docEntryGenerado.value = docEntry
            _mensajePantalla.value = "Preparando firmado factura..."

            // Obtener los datos de la base de datos
            val oinvList = withContext(Dispatchers.IO) {
                getOinvUseCase.execute(docEntry)
            }
            firmarFactura.generarStringSiedi(oinvList, "3")
        }
    }

    suspend fun existenciaPlancha(itemCode: String,  ): Int {
        return countExistenciaStockPlanchaByItemCodeUseCase.existe(itemCode )
    }


    private suspend fun cargarProductosOrden() {
        val listaProductosOrden = getStockItemPriceListCodeUseCase.Obtener()
        _productos.value = convertToProductoItems(listaProductosOrden)
        val itemCodes = listaProductosOrden.map { it.itemCode!! }.distinct()
        _itemCodes.value = itemCodes // Suponiendo que tienes una LiveData para itemCodes
    }

    private suspend fun cargarLotesProductos() {
        val itemCodes = _itemCodes.value ?: return
        val listaLotesPorProductos = getDatosDetalleLotesUseCase.Obtener(itemCodes)
        _lotesIncializados.value = listaLotesPorProductos
    }


    private suspend fun cargarDatosAdicionales() {
        //_datosOrdenVenta.value = getOrdenVentaCabByIdUseCase.Obtener(docNum)
        _datosFactura.value = getDatosFacturaUseCase.Obtener()
        _nroFactura.value = (_datosFactura.value!![0].ult_nro_fact.toInt()).toString()
        //_docNum.value = docNum
    }

    //FUNCION PASADA AL VIEWMODEL, PARA OBTENER LOS PRODUCTOS DESDE EL INICIO Y PODER USARLO AL MOMENTO DE COMPROBACION DEL BOTON REGISTRAR
    fun getProductosSeleccionados(
        articulosMenu: List<ProductoItem>,
        selections: SnapshotStateMap<String, MutableState<Boolean>>,
        quantities: SnapshotStateMap<String, MutableState<Number>>,
        quantitiesUnidad: SnapshotStateMap<String, MutableState<Int>>,
        precios: SnapshotStateMap<String, MutableState<Double>>,
        itemNames: SnapshotStateMap<String, MutableState<String>>,
    ): List<ProductosSeleccionados> {
        val selectedItems = articulosMenu.filter { selections[it.itemCode]?.value == true }
        return selectedItems.map { item ->
            ProductosSeleccionados(
                itemName = itemNames[item.itemCode]?.value!!,
                quantity = if (item.itemCode.length == 1) quantities[item.itemCode]?.value?.toDouble()  ?: 0.0 else quantities[item.itemCode]?.value?.toInt() ?: 0,
                itemCode = item.itemCode,
                priceAfVAT = precios[item.itemCode]?.value!!,
                unitMsr = item.unitMsr,
                quantityUnidad = (quantitiesUnidad[item.itemCode]?.value?.toDouble() ?: 0.0).toInt()
            )
        }
    }


    suspend fun cambiarUnitMsr(
        itemCode: String,
        nuevaUnitMsr: String,
        //priceList: Int,
     ) : listName {
        val datos = getItemUmedidaCambioUseCase.Obtener(itemCode,/* priceList,*/ nuevaUnitMsr)
        val nuevoPrecio = datos[0].price!!.toDouble()
        val nuevaUnidadMayuscula = nuevaUnitMsr.uppercase()

        // Actualiza _productos
        val productosActualizados = _productos.value?.map { producto ->
            if (producto.itemCode == itemCode) {
                producto.copy(
                    unitMsr = nuevaUnitMsr,
                    price = nuevoPrecio,
                    initialQuantity = 0,
                    baseQty = datos[0].baseQty
                )
            } else {
                producto
            }
        }
        _productos.value = productosActualizados

        // Actualiza _productosSeleccionados
        val productosActualizadosSeleccionados = _productosSeleccionados.value?.map { producto ->
            if (producto.itemCode == itemCode) {
                producto.copy(
                    unitMsr = nuevaUnitMsr,
                    priceAfVAT = nuevoPrecio,
                    itemName = producto.itemName.replace("DOCENA", "").trim() + " " + nuevaUnidadMayuscula
                )
            } else {
                producto
            }
        }
        _productosSeleccionados.value = productosActualizadosSeleccionados

        val nuevoNombre = _productosSeleccionados.value?.find { it.itemCode == itemCode }?.itemName ?: ""

        return listName(name = nuevoNombre, price = nuevoPrecio)
    }

    data class listName(
        val name: String,
        val price: Double,
    )
    fun comprobarEstadoBoton(productos: List<ProductosSeleccionados>) {
        val itemCodesSeleccionados = productos.map { it.itemCode }
        if (itemCodesSeleccionados.isNullOrEmpty()) {
            _mostrarBotonRegistrar.value = false
            _pantallaConfirmacion.value = false
        } else {
            val cantidadSugeridaIgualCantidadIngresada = productos.all { producto ->
                val cantidadIngresada = _totalIngresadoPorProducto.value[producto.itemCode] ?: 0.0
                cantidadIngresada == producto.quantityUnidad.toDouble() && cantidadIngresada != 0.0
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
        val cantidadActualizada =
            (cantidadActual + diferencia).coerceAtLeast(0.0)  // Asegura que la cantidad no sea negativa

        // Guardar la cantidad actualizada
        cantidadesActuales[itemCode] = cantidadActualizada
        _totalIngresadoPorProducto.value = cantidadesActuales
        // Llamar a la función para comprobar el estado del botón
        comprobarEstadoBoton(_productosSeleccionados.value!!)
    }

    fun inicializarProductosSeleccionados(productos: List<ProductosSeleccionados>) {
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

    private fun convertToProductoItems(ordenVentaDetItems: List<DatosItemCodesStockPriceList>): List<ProductoItem> {
        return ordenVentaDetItems.map {
            ProductoItem(
                name = it.itemName!!,
                itemCode = it.itemCode!!, // Usando itemCode como descripción por ejemplo
                price = it.price!!.toDouble(),
                initialQuantity = 0,
                unitMsr = it.unitMsr!!,
                quantityUnidad = 0,
                baseQty = it.baseQty
            )
        }
    }

    fun limpiarLista() {
        _productos.value = emptyList()
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
        _pantallaConfirmacion.value = false
    }


    fun mostrarConfirmacion() {
        _pantallaConfirmacion.value = true
    }

    fun cancelar() {
        _pantallaConfirmacion.value = false
    }

    fun processReceivedData(datosXml: String?, datosQrCdc: String?, txtSiedi: String?) {

        if (datosQrCdc.equals("null")) {
            _mensajePantalla.value = "Ha ocurrido en error al firmar \n$datosXml"
            _loadingPantalla.value = false
            _dialogPantalla.value = true
        } else {

            prepararImpresion(datosQrCdc!!, datosXml!!, txtSiedi)
        }
    }

    fun prepararImpresion(qr: String, xml: String, txtSiedi: String?) {
        _mensajePantalla.value = "Preparando impresión..."

        viewModelScope.launch {
            try {
                val listaFactura = getOinvUseCase.execute(_docEntryGenerado.value!!)
                val jsonObject = JSONObject(qr)
                updateFirmaOinvUseCase.Update(
                    jsonObject.getString("qr"),
                    xml,
                    jsonObject.getString("cdc"),
                    _docEntryGenerado.value!!,
                    txtSiedi!!
                )
                imprimir(listaFactura, jsonObject.getString("qr"), jsonObject.getString("cdc"))
            } catch (e: Exception) {
                Log.e("OinvViewModel", "Error al obtener la lista", e)
            }
        }
    }

    /**
     * IMPRESION Y FIRMADOR*/
    suspend fun imprimir(json: List<OinvPosWithDetails>, qr: String, cdc: String) {
        // Asegúrate de que las operaciones de impresión se realicen en el contexto de I/O
        _mensajePantalla.value = "Preparando impresión..."

        withContext(Dispatchers.IO) {
            val servicioBluetooth = servicioBluetooth()
            val resultadoImpresion =
                servicioBluetooth.imprimir(layoutFactura().layoutFactura(json, qr, cdc))

            // Regresa al hilo principal para actualizar la UI
            withContext(Dispatchers.Main) {
                when (resultadoImpresion) {
                    is ImpresionResultado.Exito -> {
                        val mensajeExito = resultadoImpresion.mensaje
                        // Manejar el mensaje de éxito, por ejemplo, mostrarlo en un toast o en un log
                        _mensajePantalla.value = mensajeExito
                        _loadingPantalla.value = false
                        _dialogPantalla.value = true
                        _pantallaConfirmacion.value = false
                    }

                    is ImpresionResultado.Error -> {
                        val mensajeError = resultadoImpresion.mensaje
                        // Manejar el mensaje de error, por ejemplo, mostrar un mensaje al usuario indicando el error
                        _mensajePantalla.value = mensajeError
                        _loadingPantalla.value = false
                        _dialogPantalla.value = true
                        _pantallaConfirmacion.value = false
                    }
                }
            }
        }
    }

}

data class ProductoItem(
    val name: String,
    val itemCode: String,
    val price: Double,
    val initialQuantity: Number,
    val quantityUnidad: Int,
    val unitMsr: String,
    val baseQty: Int,
)



data class ProductosSeleccionados(
    //val lineNumDet: Int,
    val itemCode: String,
    val itemName: String,
    val quantity: Number,
    val quantityUnidad: Int,
    val priceAfVAT: Double,
    val unitMsr: String

)