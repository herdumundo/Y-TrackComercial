package com.portalgm.y_trackcomercial.ui.login2

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.BuildConfig
import com.portalgm.y_trackcomercial.repository.RutasAccesosRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.logRepositories.LogRepository
import com.portalgm.y_trackcomercial.services.battery.getBatteryPercentage
import com.portalgm.y_trackcomercial.usecases.apiKeyGMS.GetApiKeyGMSUseCase
import com.portalgm.y_trackcomercial.usecases.login.AuthUseCase
import com.portalgm.y_trackcomercial.util.SharedPreferences
import com.portalgm.y_trackcomercial.util.logUtils.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPreferences: SharedPreferences,
    private val rutasAccesosRepository: RutasAccesosRepository,
    private val getApiKeyGMSUseCase: GetApiKeyGMSUseCase,
    private val logRepository: LogRepository,
    private val context: Context


    ) : ViewModel() {
    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> = _apiKey

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    private val _mensajeBoton = MutableLiveData<String>()
    val mensajeBoton: LiveData<String> = _mensajeBoton

    private val _botonCargar = MutableLiveData<Int>()

    /**
     * Parametro para variacion del estado del botón iniciar sesión.
     *
     * @param 1 = Ingrese Datos.
     * @param 2 = Iniciar Sesión.
     * @param 3 = Iniciando.
     * @param 4 = Usuario incorrecto.
     */
    val botonCargar: LiveData<Int> = _botonCargar


    fun onLoginChanged(userName: String, password: String) {
        _userName.value = userName
        _password.value = password
    }

    fun onDialogClose() {

        // _loggedIn.value = loggedIn.value != true
        _loggedIn.value = false
    }


    fun autenticacionLogin(home: () -> Unit) {

        viewModelScope.launch {

            _loading.value = true
            _botonCargar.value = 3
            _mensajeBoton.value = " Iniciando..."

            val result = authUseCase(userName.value!!, password.value!!)

            if (result?.Sesion == true) {
               sharedPreferences.savePreferences(
                    result.Usuario[0].yemsys_role.descripcion,
                    (result.Usuario[0].nombre + " " + result.Usuario[0].apellido),
                    "Bearer "+result.Token,
                    password.value!!,
                    userName.value!!,
                    result.Usuario[0].id,
                    result.RutasAccesos,
                  //  getApiKeyGMSUseCase.getApiKeyGMS()
                )

                rutasAccesosRepository.deleteAndInsertAllRutasAccesos(result.RutasAccesos)

                _mensajeBoton.value = "Iniciar Sesión"
                _botonCargar.value = 2
                _loggedIn.value = true

                sharedPreferences.saveApiGms(getApiKeyGMSUseCase.getApiKeyGMS())
                fetchApiKey()
                home()

                val porceBateria = getBatteryPercentage(context)

                LogUtils.insertLog(
                    logRepository,
                    LocalDateTime.now().toString(),
                    "Inicio se sesión ",
                    "Ha iniciado sesión",
                    sharedPreferences.getUserId(),
                    sharedPreferences.getUserName().toString(),
                    "Version App "+ BuildConfig.VERSION_NAME,
                    porceBateria
                )

            }
            else if(result==null){
                _mensajeBoton.value = "Verifique conexion a internet."
                _botonCargar.value = 5
                _loggedIn.value = false

            }
            else {
                _mensajeBoton.value = "Error de usuario/contraseña, reintentar."
                _botonCargar.value = 4
                _loggedIn.value = false

            }
            _loading.value = false
        }
    }
    fun fetchApiKey() {
         _apiKey.value = sharedPreferences.getApiKeyGms()
        Log.i("MensajeApi", _apiKey.value!!)
    }
}