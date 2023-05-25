package com.example.y_trackcomercial.ui.login2

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.ui.login2.domain.AuthUseCase
import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {

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
            _mensajeBoton.value = "Iniciando..."

            val result = authUseCase(userName.value!!, password.value!!)

            if (result?.Sesion == true) {

                sharedPreferences.savePreferences(
                    result.Datos[0].yemsys_role.descripcion,
                    (result.Datos[0].nombre + " " + result.Datos[0].apellido),
                    result.Token,
                    result.RutasAccesos
                )

                _mensajeBoton.value = "Iniciar Sesión"
                _botonCargar.value = 2
                _loggedIn.value = true
                home()

            } else {
                _mensajeBoton.value = "Error al autenticarse, verifique datos."
                _botonCargar.value = 4
                _loggedIn.value = false

            }
            _loading.value = false
        }
    }


}