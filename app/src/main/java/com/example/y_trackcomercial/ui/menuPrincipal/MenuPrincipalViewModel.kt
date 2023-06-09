package com.example.y_trackcomercial.ui.menuPrincipal

import RutasAccesos
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.repository.LotesListasRepository
import com.example.y_trackcomercial.repository.UsuarioRepository
import com.example.y_trackcomercial.repository.CustomerRepository
import com.example.y_trackcomercial.data.network.response.OCRD
 import com.example.y_trackcomercial.model.entities.RutasAccesosEntity
import com.example.y_trackcomercial.repository.HorariosUsuarioRepository
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.example.y_trackcomercial.repository.PermisosVisitasRepository
import com.example.y_trackcomercial.repository.RutasAccesosRepository
import com.example.y_trackcomercial.services.developerMode.DeveloperModeListener
import com.example.y_trackcomercial.services.developerMode.DeveloperModeObserver
import com.example.y_trackcomercial.ui.login2.domain.AuthUseCase
import kotlinx.coroutines.withContext
import android.os.Handler
import android.provider.Settings
import com.example.y_trackcomercial.services.developerMode.isDeveloperModeEnabled

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
  //  private var developerModeObserver: DeveloperModeObserver,
    private val context: Context


) : ViewModel() {


    private val _rol = mutableStateOf("")
    val rol: State<String> = _rol


    private val _customers = MutableLiveData<List<OCRD>>()
    val customers: LiveData<List<OCRDEntity>> = customerRepository.customers

    private val _progress: MutableLiveData<Float> = MutableLiveData()
    val progress: LiveData<Float> = _progress

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showLoadingOk: MutableLiveData<Boolean> = MutableLiveData()
    val showLoadingOk: LiveData<Boolean> = _showLoadingOk

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


    init {
        obtenerRutasAccesosDesdeRoom()
     }


    fun getOCRD() {
        _showLoading.value = true
        viewModelScope.launch(Dispatchers.Main)  {

             _mensajeDialog.value="Cargando Lotes..."

            lotesListasRepository.fetchLotesListas { progress ->
                _progress.postValue(progress)
            }

            _mensajeDialog.value="Cargando Clientes..."
            customerRepository.fetchCustomers { progress ->
                _progress.postValue(progress)
            }

            _mensajeDialog.value="Cargando Ubicaciones..."
            ocrdUbicacionesRepository.fetchOcrdUbicaciones {  progress ->
                _progress.postValue(progress)
            }

            _mensajeDialog.value="Cargando Permisos ..."

            val result = authUseCase(getUserLogin(), getPasswordUserName())
            rutasAccesosRepository.deleteAndInsertAllRutasAccesos(result!!.RutasAccesos)

            obtenerRutasAccesosDesdeRoom()


            _mensajeDialog.value="Cargando Permisos de visitas ..."
            permisosVisitasRepository.fetchPermisosVisitas(getUserId())


            _mensajeDialog.value="Cargando horarios ..."
            horariosUsuarioRepository.fetchHorariosUsuario(getUserId())

            _mensajeDialog.value="Datos importados correctamente."
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