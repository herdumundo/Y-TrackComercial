package com.portalgm.y_trackcomercial.ui.cambioPass.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import com.portalgm.y_trackcomercial.usecases.newPass.InsertarNewPassUseCase
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CambioPassViewModel @Inject constructor(

    private val insertarNewPassUseCase: InsertarNewPassUseCase,
    private val sharedPreferences: SharedPreferences,


    ): ViewModel()
{
    private val _currentPassword: MutableLiveData<String> = MutableLiveData()
    val currentPassword: LiveData<String> = _currentPassword

    private val _newPassword: MutableLiveData<String> = MutableLiveData()
    val newPassword: LiveData<String> = _newPassword

    private val _confirmNewPassword: MutableLiveData<String> = MutableLiveData()
    val confirmNewPassword: LiveData<String> = _confirmNewPassword

    private val _mensajeBoton: MutableLiveData<String> = MutableLiveData()
    val  mensajeBoton: LiveData<String> = _mensajeBoton

    private val _mensajeAlerta: MutableLiveData<String> = MutableLiveData()
    val  mensajeAlerta: LiveData<String> = _mensajeAlerta

    private val snackbarDuration = 3000L

    private val _alerta = MutableLiveData<Boolean>()
    val alerta: LiveData<Boolean> = _alerta

    fun onNewPassChanged(newPass: String) {
        _newPassword.value = newPass
    }

    fun onCurrentPass(newPass: String) {
        _currentPassword.value = newPass
    }

    fun onConfirmNewPassChanged(newPass: String) {
        _confirmNewPassword.value = newPass
    }

    fun insertarDatos (){

        viewModelScope.launch {
            insertarNewPassUseCase.Insertar(
                NewPassEntity(
                id=System.currentTimeMillis(),
                idUsuario = sharedPreferences.getUserId(),
                estado="P",
                newPass = _confirmNewPassword.value.toString()
            )
            )
             viewModelScope.launch {
                 _alerta.value=true
                 _mensajeBoton.value="Cambiar Contraseña"
                 _mensajeAlerta.value="Contraseña modificada"
                 delay(snackbarDuration)
                 _alerta.value=false

                 _newPassword.value=""
                 _confirmNewPassword.value=""
            }
        }

    }
    fun errorDatos (){
        viewModelScope.launch {
            _alerta.value=true
            _mensajeBoton.value="Las contraseñas no coinciden"
            _mensajeAlerta.value="Las contraseñas no coinciden"
            delay(snackbarDuration)
            _alerta.value=false
            _mensajeBoton.value="Cambiar Contraseña"

        }
    }

    fun camposVacios (){
        viewModelScope.launch {
            _alerta.value=true
            _mensajeBoton.value="Complete los datos"
            _mensajeAlerta.value="Complete los datos"
            delay(snackbarDuration)
            _alerta.value=false

            _mensajeBoton.value="Cambiar Contraseña"

        }
    }

    fun limpiarDatos(){
        _newPassword.value=""
        _confirmNewPassword.value=""
    }
}

