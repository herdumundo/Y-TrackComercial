package com.portalgm.y_trackcomercial.ui.menuPrincipal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.data.api.response.OCRD
import com.portalgm.y_trackcomercial.data.model.entities.RutasAccesosEntity
import com.portalgm.y_trackcomercial.repository.HorariosUsuarioRepository
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.portalgm.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.portalgm.y_trackcomercial.repository.RutasAccesosRepository
import com.portalgm.y_trackcomercial.usecases.login.AuthUseCase
import kotlinx.coroutines.withContext
import com.portalgm.y_trackcomercial.repository.OcrdOitmRepository
import com.portalgm.y_trackcomercial.repository.OitmRepository
import com.portalgm.y_trackcomercial.usecases.parametros.ImportarParametrosUseCase
import com.portalgm.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.listaPrecios.ImportarListaPreciosUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.ordenVenta.ImportarOrdenVentaUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.stockAlmacen.ImportarStockAlmacenUseCase
import com.portalgm.y_trackcomercial.usecases.ventas.vendedores.ImportarVendedoresUseCase
import com.portalgm.y_trackcomercial.util.SharedData

@HiltViewModel
class MenuPrincipalViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val customerRepository: CustomerRepository,
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository,
    private val rutasAccesosRepository: RutasAccesosRepository,
    private val authUseCase: AuthUseCase,
    private val horariosUsuarioRepository: HorariosUsuarioRepository,
    private val ocrdOitmRepository: OcrdOitmRepository,
    private val oitmRepository: OitmRepository,
    private val importarUbicacionesPvUseCase: ImportarUbicacionesPvUseCase,
    private val importarParametrosUseCase: ImportarParametrosUseCase,
    private val importarVendedoresUseCase: ImportarVendedoresUseCase,
    private val importarListaPreciosUseCase: ImportarListaPreciosUseCase,
    private val importarStockAlmacenUseCase: ImportarStockAlmacenUseCase,
    private val importarOrdenVentaUseCase: ImportarOrdenVentaUseCase,
    ) : ViewModel() {


    private val _rol = mutableStateOf("")
    val rol: State<String> = _rol


    private val _customers = MutableLiveData<List<OCRD>>()
    val customers: LiveData<List<com.portalgm.y_trackcomercial.data.model.entities.OCRDEntity>> =
        customerRepository.customers


    private val _progress: MutableLiveData<Float> = MutableLiveData()
    val progress: LiveData<Float> = _progress

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showLoadingOk: MutableLiveData<Boolean> = MutableLiveData()
    val showLoadingOk: LiveData<Boolean> = _showLoadingOk

    private val _showDialogNewPass: MutableLiveData<Boolean> = MutableLiveData()
    val showDialogNewPass: LiveData<Boolean> = _showDialogNewPass


    private val _mensajeDialog: MutableLiveData<String> = MutableLiveData()
    val mensajeDialog: LiveData<String> = _mensajeDialog

    private val _permisosUsuarios: MutableLiveData<List<RutasAccesosEntity>> = MutableLiveData()
    val permisosUsuarios: LiveData<List<RutasAccesosEntity>> = _permisosUsuarios

    fun getUserName(): String = sharedPreferences.getUserName().toString()
    fun getRol(): String = sharedPreferences.getRol().toString()
    fun getPasswordUserName(): String = sharedPreferences.getPasswordUserName().toString()
    fun getUserLogin(): String = sharedPreferences.getUserLogin().toString()
    fun getUserId(): Int = sharedPreferences.getUserId()

    //  fun getRutasAccesos(): List<RutasAccesos> = sharedPreferences.getRutasAccesos()
    val sharedData = SharedData.getInstance()

    val webSocketConectado: LiveData<Boolean> = sharedData.webSocketConectado

    init {
        obtenerRutasAccesosDesdeRoom()
    }


    fun getOCRD() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            try {

                _mensajeDialog.value = "Cargando Clientes..."
                customerRepository.fetchCustomers()

                _mensajeDialog.value = "Cargando Ubicaciones..."
                ocrdUbicacionesRepository.fetchOcrdUbicaciones()

                _mensajeDialog.value = "Cargando Permisos ..."

                val result = authUseCase(getUserLogin(), getPasswordUserName())
                rutasAccesosRepository.deleteAndInsertAllRutasAccesos(result!!.RutasAccesos)
                obtenerRutasAccesosDesdeRoom()

                _mensajeDialog.value = "Cargando Parametros ..."
                importarParametrosUseCase.importarParametros()

                _mensajeDialog.value = "Cargando horarios ..."
                horariosUsuarioRepository.fetchHorariosUsuario(getUserId())

                _mensajeDialog.value = "Cargando productos ..."
                oitmRepository.fetchOitm()

                _mensajeDialog.value = "Cargando productos por punto venta ..."
                ocrdOitmRepository.fetchOcrdOitm()

                _mensajeDialog.value = "Cargando depositos ..."
                importarUbicacionesPvUseCase.fetchUbicacionesPv()

                _mensajeDialog.value = "Cargando vendedores ..."
                importarVendedoresUseCase.Importar()

                _mensajeDialog.value = "Cargando lista de precios ..."
                importarListaPreciosUseCase.Importar()

                _mensajeDialog.value = "Cargando stock por almacen ..."
                importarStockAlmacenUseCase.Importar()

                _mensajeDialog.value = "Cargando ordenes de ventas ..."
                importarOrdenVentaUseCase.Importar()

                _mensajeDialog.value = "Datos importados correctamente."
                _showLoading.value = false
                _showLoadingOk.value = true
            } catch (e: Exception) {
                _mensajeDialog.value =
                    "Ha ocurrido un error al obtener los datos, verifique internet."
                _showLoading.value = false
                _showLoadingOk.value = true
            }
        }


    }

    fun cerrarAviso() {
        _showLoadingOk.value = false
        _showDialogNewPass.value = false
    }

    private fun obtenerRutasAccesosDesdeRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            val rutasAccesos = rutasAccesosRepository.getAllRutasAccesos()
            withContext(Dispatchers.Main) {
                _permisosUsuarios.value = rutasAccesos
            }
        }
    }
}