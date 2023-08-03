package com.example.y_trackcomercial.ui.inventario.viewmodel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.model.models.Lotes
import com.example.y_trackcomercial.model.models.LotesItem
import com.example.y_trackcomercial.model.models.OitmItem
import com.example.y_trackcomercial.model.models.UbicacionPv
import com.example.y_trackcomercial.repository.LotesListasRepository
import com.example.y_trackcomercial.repository.OitmRepository
import com.example.y_trackcomercial.repository.registroRepositories.MovimientosRepository
import com.example.y_trackcomercial.usecases.marcacionPromotora.GetIdVisitaActivaUseCase
import com.example.y_trackcomercial.usecases.ubicacionesPv.GetUbicacionesPvUseCase
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class InventarioViewModel @Inject constructor(
    private val oitmRepository: OitmRepository,
    private val movimientosRepository: MovimientosRepository,
    private val lotesListasRepository: LotesListasRepository,
    private val getUbicacionesPvUseCase: GetUbicacionesPvUseCase,
    private val getIdVisitaActivaUseCase: GetIdVisitaActivaUseCase,
    private val sharedPreferences: SharedPreferences,


    ) : ViewModel() {
    private val _oitmItemList: MutableList<OitmItem> = mutableListOf()
    private val _ubicacionesList: MutableList<UbicacionPv> = mutableListOf()
    private val _LoteItemList: MutableList<LotesItem> = mutableListOf()


    private val _textButtonProducto: MutableLiveData<String> = MutableLiveData()
    val textButtonProducto: MutableLiveData<String> = _textButtonProducto

    private val _textButtonUbicacion: MutableLiveData<String> = MutableLiveData()
    val textButtonUbicacion: MutableLiveData<String> = _textButtonUbicacion

    private val _textButtonLote: MutableLiveData<String> = MutableLiveData()
    val textButtonLote: MutableLiveData<String> = _textButtonLote

    private val _itemCode: MutableLiveData<String> = MutableLiveData()
    private val _itemName: MutableLiveData<String> = MutableLiveData()
    private val _idLote: MutableLiveData<String> = MutableLiveData()
    private val _codeBar: MutableLiveData<String> = MutableLiveData()
    private val _descUbicacion: MutableLiveData<String> = MutableLiveData()
    val idLote: MutableLiveData<String> = _idLote

    private val _txtCantidad = MutableLiveData<String>()
    val txtCantidad: LiveData<String> = _txtCantidad

    private val _showDialogLote: MutableLiveData<Boolean> = MutableLiveData(false)
    val showDialogLote: LiveData<Boolean> = _showDialogLote

    private val _showButtonLote: MutableLiveData<Boolean> = MutableLiveData(false)
    val showButtonLote: LiveData<Boolean> = _showButtonLote

    val lotesList = mutableStateListOf<Lotes>()
    private val snackbarDuration = 3000L

    private val _snackbarMessage = MutableLiveData<String>()
    val snackbarMessage: LiveData<String> = _snackbarMessage

    private val _showDialogDelete: MutableLiveData<Boolean> = MutableLiveData(false)
    val showDialogDelete: LiveData<Boolean> = _showDialogDelete

    private val _showDialogRegistrar: MutableLiveData<Boolean> = MutableLiveData(false)
    val showDialogRegistrar: LiveData<Boolean> = _showDialogRegistrar

    private val _colorSnack: MutableLiveData<Long> = MutableLiveData()
    val colorSnack: LiveData<Long> = _colorSnack

    private val _iConoSnack: MutableLiveData<ImageVector> = MutableLiveData()
    val iConoSnack: LiveData<ImageVector> = _iConoSnack

    var _posicionFila=0


    var idVisita=0

    fun addLotes() {

        if (_txtCantidad.value.isNullOrBlank() || _txtCantidad.value!!.toInt() == 0) {
            showSnackbar("Ingrese cantidad.",0xFF161010, Icons.Default.Warning)
            return
        }
        if (_descUbicacion.value.isNullOrBlank()) {
            showSnackbar("Seleccione ubicacion.", 0xFF161010, Icons.Default.Warning)
            return
        }
        val lote = _idLote.value ?: ""
        val ubicacion = _descUbicacion.value ?: ""

        // Verificar si ya existe un lote con los mismos valores en la lista
        val loteExistente = lotesList.any {  it.Lote == lote && it.ubicacion == ubicacion }
        if (loteExistente) {
            showSnackbar("El lote ya existe en la lista.", 0xFF161010, Icons.Default.Warning)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            idVisita=getIdVisitaActivaUseCase.getActiveVisitId()
            viewModelScope.launch(Dispatchers.Main){
            val createdAtLongVar=System.currentTimeMillis()
            val newLote = Lotes(
                ItemName = _itemName.value!!,
                Lote=       _idLote.value!!,
                Cantidad=   _txtCantidad.value!!.toInt(),
                ubicacion=  _descUbicacion.value!!,
                idUsuario=  sharedPreferences.getUserId(),
                userName =  sharedPreferences.getUserName()!!,
                tipoMovimiento = 1,
                createdAt = LocalDateTime.now().toString(),
                createdAtLong =createdAtLongVar ,
                codeBars=   _codeBar.value ?: "",
                idVisitas = idVisita,
                loteLargo = _idLote.value!!,
                loteCorto = _idLote.value!!,
                obs="",
                itemCode = _itemCode.value!!
            )

            lotesList.add(newLote)
                for (lote in lotesList) {
                    Log.i("Mensaje", lote.toString())
                }
                setShowButtonLote(false)
                setValoresIniciales()
            }
        }
    }

    fun setShowDialogLote(show: Boolean) {
        _showDialogLote.value = show
    }

    fun setShowButtonLote(show: Boolean) {
        _showButtonLote.value = show
    }

    fun setValoresIniciales() {
        _textButtonProducto.value = "Seleccione producto"
        _textButtonUbicacion.value = "Seleccione ubicacion"
        _textButtonLote.value = "Seleccione Lote"
        _idLote.value = ""
        _descUbicacion.value = ""
        _itemCode.value = ""
        _itemName.value = ""
        _txtCantidad.value = ""
        _LoteItemList.clear()
    }
    fun limpiarDatos() {
        _textButtonProducto.value = "Seleccione producto"
        _textButtonUbicacion.value = "Seleccione ubicacion"
        _textButtonLote.value = "Seleccione Lote"
        _idLote.value = ""
        _descUbicacion.value = ""
        _itemCode.value = ""
        _itemName.value = ""
        _txtCantidad.value = ""
        _LoteItemList.clear()
        lotesList.clear()
    }

    fun showSnackbar(message: String, Color: Long, icono: ImageVector) {
        _snackbarMessage.value = message
        _colorSnack.value=Color
        _iConoSnack.value=icono
        //_colorSnack.value=0xFF161010
        // Iniciar el temporizador para borrar el mensaje del Snackbar después de 3 segundos
        viewModelScope.launch {
            delay(snackbarDuration)

            _snackbarMessage.value = ""
        }
    }

    fun onCantidadChanged(cantidad: String) {
        _txtCantidad.value = cantidad
    }

    fun setProducto(ItemName: String, ItemCode: String) {

        _textButtonProducto.value = ItemName
        _itemCode.value = ItemCode
        _itemName.value = ItemName
        setLotesListas(ItemCode)

    }

    fun setLote(Id: String, codeBars: String) {
        _idLote.value = Id
        _codeBar.value=codeBars
        _textButtonLote.value = Id
    }

    fun setUbicacion(descripcion: String) {
        _descUbicacion.value = descripcion
        _textButtonUbicacion.value = descripcion
    }

    fun setOitm() {
        _LoteItemList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val oitm = oitmRepository.getOitmByPuntoVentaOpen()
            withContext(Dispatchers.Main) {
                _oitmItemList.clear()
                _oitmItemList.addAll(oitm)
            }
        }
    }

    fun setUbicaciones() {
        _ubicacionesList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val ubicaciones = getUbicacionesPvUseCase.getUbicaciones()
            withContext(Dispatchers.Main) {
                _ubicacionesList.clear()
                _ubicacionesList.addAll(ubicaciones)
            }
        }
    }

    fun setLotesListas(itemCode: String) {

        _textButtonLote.value = "No se encuentran lotes"
        _idLote.value = ""
        _txtCantidad.value = ""
        viewModelScope.launch(Dispatchers.IO) {
            val lotesLista = lotesListasRepository.getLotesListasByItemCode(itemCode)
            withContext(Dispatchers.Main) {
                _LoteItemList.clear()
                _LoteItemList.addAll(lotesLista)
                setShowDialogLote(true)
            }
        }

    }

    fun getUbicaciones(): List<UbicacionPv> {
        return _ubicacionesList
    }

    fun getOitm(): List<OitmItem> {
        return _oitmItemList
    }

    fun getLotes(): List<LotesItem> {
        return _LoteItemList
    }

    fun registrarInventario(){
        if (lotesList.isEmpty()) {
            showSnackbar("Debes cargar articulo.", 0xFF161010, Icons.Default.Warning)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            movimientosRepository.insertLotesInBulk(lotesList)
            viewModelScope.launch(Dispatchers.Main) {
                cerrarDialogRegistro()
                showSnackbar("Registrado con exito.", 0xFF1C6900, Icons.Default.ThumbUp)

                limpiarDatos()
            }
        }
    }

    fun consultaRegistro(){
        _showDialogRegistrar.value=true

    }

    fun consultaRemoverFilar(index:Int){
        _showDialogDelete.value=true
        _posicionFila=index
    }
    fun confirmaRemoverFila(){
        lotesList.removeAt(_posicionFila)
        cerrarDialogRemoverFila()
    }
    fun cerrarDialogRemoverFila(){
        _showDialogDelete.value=false
    }

    fun cerrarDialogRegistro(){
        _showDialogRegistrar.value=false
    }
}