package com.example.y_trackcomercial.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.y_trackcomercial.ui.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _botonCargar = MutableLiveData<Boolean>()
    val botonCargar: LiveData<Boolean> = _botonCargar


    fun onLoginChanged(userName: String, password: String) {
        _userName.value = userName
        _password.value = password

    }

    fun onDialogClose() {

        // _loggedIn.value = loggedIn.value != true
        _loggedIn.value = false
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Check if username and password are valid
        return false // Replace with actual implementation
    }


    fun onLoginSelected() {
        viewModelScope.launch {
            _botonCargar.value = true

            if (userName.value.isNullOrBlank() || password.value.isNullOrBlank()) {
                _botonCargar.value = false

            } else {

                val result = loginUseCase(userName.value!!, password.value!!)
                if (result) {
                    //Navegar a la siguiente pantalla
                    Log.i("aris", "result OK")
                    _botonCargar.value=false
                    _loggedIn.value=false

                }

                //  val result = loginUseCase(userName.value!!, password.value!!)
                //  val usuario = loginRepository.login(userName.value!!, password.value!!)
                /*if (usuario != null) {
                    Log.i("Respuesta", "result OK "+usuario.nombre)
                }*/
                /*if(usuario.nombre){
                    //Navegar a la siguiente pantalla
                    Log.i("Respuesta", "result OK "+result.username)

                 }
                else {
                    _botonCargar.value=false
                    _loggedIn.value=true
                    Log.i("Respuesta", "result OK "+result.username)

                }*/
            }


        }
    }

    fun login(username: String, password: String) {
        if (isValidCredentials(username, password)) {
            Log.i("usuario", username)
            Log.i("password", password)
        } else {
            _loggedIn.value = true
        }
    }
}
