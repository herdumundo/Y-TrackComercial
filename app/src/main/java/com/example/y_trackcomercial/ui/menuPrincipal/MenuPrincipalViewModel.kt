package com.example.y_trackcomercial.ui.menuPrincipal

import RutasAccesos
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
import com.example.y_trackcomercial.model.dao.RutasAccesosDao
import com.example.y_trackcomercial.model.entities.RutasAccesosEntity
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.y_trackcomercial.repository.OcrdUbicacionesRepository
import com.example.y_trackcomercial.repository.RutasAccesosRepository
import com.example.y_trackcomercial.ui.login2.domain.AuthUseCase
import kotlinx.coroutines.withContext

@HiltViewModel
class MenuPrincipalViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val customerRepository: CustomerRepository,
    private val usuarioRepository: UsuarioRepository,
    private val lotesListasRepository: LotesListasRepository,
    private val ocrdUbicacionesRepository: OcrdUbicacionesRepository,
    private val rutasAccesosRepository: RutasAccesosRepository,
    private val authUseCase: AuthUseCase,


    ) : ViewModel() {


    private val _rol = mutableStateOf("")
    val rol: State<String> = _rol

   // private val _rutas = sharedPreferences.getRutasAccesos() //mutableStateListOf<RutasAccesos>()//
   // val rutas: List<RutasAccesos> = _rutas
   private val _rutas: MutableLiveData<List<RutasAccesosEntity>> = MutableLiveData()
    val rutas: LiveData<List<RutasAccesosEntity>> = _rutas

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

    fun getUserName(): String = sharedPreferences.getUserName().toString()
    fun getRol(): String = sharedPreferences.getRol().toString()
    fun getPasswordUserName(): String = sharedPreferences.getPasswordUserName().toString()
    fun getUserLogin(): String = sharedPreferences.getUserLogin().toString()
  //  fun getRutasAccesos(): List<RutasAccesos> = sharedPreferences.getRutasAccesos()

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

            _mensajeDialog.value="Cargando Permisos Otorgados..."

            val result = authUseCase(getUserLogin(), getPasswordUserName())
            rutasAccesosRepository.fetchRutasAccesos(result!!.RutasAccesos)


            _mensajeDialog.value="Datos importados correctamente."
            _showLoading.value = false
            _showLoadingOk.value = true
        }

    }

    fun cerrarAviso() {
        _showLoadingOk.value = false
    }


   /* private fun obtenerRutasAccesosDesdeRoom() {
        viewModelScope.launch {
            val rutasAccesos = rutasAccesosDao.getAllRutasaccesos()
            _rutas.postValue(rutasAccesos)
        }
    }*/
   private fun obtenerRutasAccesosDesdeRoom() {
       viewModelScope.launch(Dispatchers.IO) {
           val rutasAccesos = rutasAccesosRepository.getAllRutasAccesos()
           withContext(Dispatchers.Main) {
               _rutas.value = rutasAccesos
           }
       }
   }


    init {
        obtenerRutasAccesosDesdeRoom()
    }
}