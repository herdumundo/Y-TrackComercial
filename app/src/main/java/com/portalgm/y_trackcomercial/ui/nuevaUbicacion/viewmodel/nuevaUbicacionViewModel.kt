package com.portalgm.y_trackcomercial.ui.nuevaUbicacion.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portalgm.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgm.y_trackcomercial.data.model.models.OcrdItem
import com.portalgm.y_trackcomercial.repository.CustomerRepository
import com.portalgm.y_trackcomercial.repository.registroRepositories.NuevaUbicacionRepository
import com.portalgm.y_trackcomercial.services.gps.locatioGoogleMaps.LocationService
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NuevaUbicacionViewModel @Inject constructor(
        private val context: Context,
        private val customerRepository: CustomerRepository,
        private val sharedPreferences: SharedPreferences,
        private val nuevaUbicacionRepository:NuevaUbicacionRepository
    ) : ViewModel() {
    private val  locationService: LocationService = LocationService()
    private val _latitud: MutableLiveData<Double> = MutableLiveData()
    var latitud: MutableLiveData<Double> = _latitud

    private val _longitud: MutableLiveData<Double> = MutableLiveData()
    val longitud: MutableLiveData<Double> = _longitud

    private val _ocrdName: MutableLiveData<String> = MutableLiveData()
    val ocrdName: MutableLiveData<String> = _ocrdName

    private val _idOcrd: MutableLiveData<String> = MutableLiveData()
    val idOcrd: MutableLiveData<String> = _idOcrd

    private val _showButtonPv = MutableLiveData<Boolean>()
    val showButtonPv: LiveData<Boolean> = _showButtonPv

    private val _buttonPvText = MutableLiveData<String>()
    val buttonPvText: LiveData<String> = _buttonPvText

    private val _showButtonSelectPv = MutableLiveData<Boolean>()
    val showButtonSelectPv: LiveData<Boolean> = _showButtonSelectPv
    private val _registrado = MutableLiveData<Boolean>()
    val registrado: LiveData<Boolean> = _registrado

    private val snackbarDuration = 3000L


    private val _addressesList: MutableList<OcrdItem> = mutableListOf()

    fun obtenerUbicacion(){
        viewModelScope.launch {
            val resultLocation= locationService.getUserLocation(context)
            _longitud.value = resultLocation?.longitude ?: 0.0
            _latitud.value = resultLocation?.latitude ?: 0.0
            Log.i("Ubicacion","Latitud "+resultLocation?.latitude.toString()+"  Longitud "+resultLocation?.longitude.toString())
         }
        inicializarValores()
    }

    fun getAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            val addresses = customerRepository.getAddresses()
            withContext(Dispatchers.Main) {
                _addressesList.clear()
                _addressesList.addAll(addresses)
            }
        }
    }
    fun getStoredAddresses(): List<OcrdItem> {
        return _addressesList
    }

    fun setIdOcrd(idOcrd: String, ocrd: String) {
        _idOcrd.value = idOcrd
        _ocrdName.value = ocrd
        _showButtonPv.value = true
        _buttonPvText.value = ocrd
    }
    fun inicializarValores () {
        _buttonPvText.value = "Seleccionar punto de venta"
        _idOcrd.value = ""
        _ocrdName.value = ""
        _showButtonPv.value = false
    }

    fun registrar()
    {
        viewModelScope.launch {
            nuevaUbicacionRepository.insertNuevaUbicacion(UbicacionesNuevasEntity(
                id=System.currentTimeMillis(),
                idOcrd=idOcrd.value!!,
                idUsuario = sharedPreferences.getUserId(),
                createdAt = LocalDateTime.now().toString(),
                latitudPV = _latitud.value!!,
                longitudPV = _longitud.value!!,
                estado="P"
            ))
            inicializarValores()
            _registrado.value=true
            viewModelScope.launch {
                delay(snackbarDuration)
                _registrado.value = false
            }
        }

    }


}