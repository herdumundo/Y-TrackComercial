package com.ytrack.y_trackcomercial.ui.menuPrincipal

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ytrack.y_trackcomercial.repository.LotesListasRepository
import com.ytrack.y_trackcomercial.repository.UsuarioRepository
import com.ytrack.y_trackcomercial.repository.CustomerRepository
import com.ytrack.y_trackcomercial.data.api.response.OCRD
import com.ytrack.y_trackcomercial.repository.HorariosUsuarioRepository
import com.ytrack.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ytrack.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.ytrack.y_trackcomercial.repository.PermisosVisitasRepository
import com.ytrack.y_trackcomercial.repository.RutasAccesosRepository
import com.ytrack.y_trackcomercial.usecases.login.AuthUseCase
import kotlinx.coroutines.withContext
import com.ytrack.y_trackcomercial.repository.OcrdOitmRepository
import com.ytrack.y_trackcomercial.repository.OitmRepository
import com.ytrack.y_trackcomercial.usecases.ubicacionesPv.ImportarUbicacionesPvUseCase

@HiltViewModel
class MenuPrincipalViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val customerRepository: CustomerRepository,
    private val usuarioRepository: UsuarioRepository,
    private val lotesListasRepository: LotesListasRepository,
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository,
    private val rutasAccesosRepository: RutasAccesosRepository,
    private val permisosVisitasRepository: PermisosVisitasRepository,
    private val authUseCase: AuthUseCase,
    private val horariosUsuarioRepository: HorariosUsuarioRepository,
    private val ocrdOitmRepository: OcrdOitmRepository,
    private val oitmRepository: OitmRepository,
    private val importarUbicacionesPvUseCase: ImportarUbicacionesPvUseCase,
    //  private var developerModeObserver: DeveloperModeObserver,
    private val context: Context


) : ViewModel() {


    private val _rol = mutableStateOf("")
    val rol: State<String> = _rol


    private val _customers = MutableLiveData<List<OCRD>>()
    val customers: LiveData<List<com.ytrack.y_trackcomercial.data.model.entities.OCRDEntity>> = customerRepository.customers

    private val _progress: MutableLiveData<Float> = MutableLiveData()
    val progress: LiveData<Float> = _progress

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showLoadingOk: MutableLiveData<Boolean> = MutableLiveData()
    val showLoadingOk: LiveData<Boolean> = _showLoadingOk

    private val _mensajeDialog: MutableLiveData<String> = MutableLiveData()
    val mensajeDialog: LiveData<String> = _mensajeDialog

    private val _permisosUsuarios: MutableLiveData<List<com.ytrack.y_trackcomercial.data.model.entities.RutasAccesosEntity>> = MutableLiveData()
    val permisosUsuarios: LiveData<List<com.ytrack.y_trackcomercial.data.model.entities.RutasAccesosEntity>> = _permisosUsuarios

    fun getUserName(): String = sharedPreferences.getUserName().toString()
    fun getRol(): String = sharedPreferences.getRol().toString()
    fun getPasswordUserName(): String = sharedPreferences.getPasswordUserName().toString()
    fun getUserLogin(): String = sharedPreferences.getUserLogin().toString()
    fun getUserId(): Int = sharedPreferences.getUserId()
    //  fun getRutasAccesos(): List<RutasAccesos> = sharedPreferences.getRutasAccesos()


    init {
        obtenerRutasAccesosDesdeRoom()
    }


    fun getOCRD() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {

            _mensajeDialog.value = "Cargando Lotes..."

            lotesListasRepository.fetchLotesListas()

            _mensajeDialog.value = "Cargando Clientes..."
            customerRepository.fetchCustomers()


            _mensajeDialog.value = "Cargando Ubicaciones..."
            ocrdUbicacionesRepository.fetchOcrdUbicaciones()

            _mensajeDialog.value = "Cargando Permisos ..."

            val result = authUseCase(getUserLogin(), getPasswordUserName())
            rutasAccesosRepository.deleteAndInsertAllRutasAccesos(result!!.RutasAccesos)

            obtenerRutasAccesosDesdeRoom()


            _mensajeDialog.value = "Cargando Permisos de visitas ..."
            permisosVisitasRepository.fetchPermisosVisitas(getUserId())

            _mensajeDialog.value = "Cargando horarios ..."
            horariosUsuarioRepository.fetchHorariosUsuario(getUserId())

            _mensajeDialog.value = "Cargando productos ..."
            oitmRepository.fetchOitm()

            _mensajeDialog.value = "Cargando productos por punto venta ..."
            ocrdOitmRepository.fetchOcrdOitm()

            _mensajeDialog.value = "Cargando depositos ..."
            importarUbicacionesPvUseCase.fetchUbicacionesPv()

            _mensajeDialog.value = "Datos importados correctamente."
            _showLoading.value = false
            _showLoadingOk.value = true
        }

    }

    fun cerrarAviso() {
        _showLoadingOk.value = false
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