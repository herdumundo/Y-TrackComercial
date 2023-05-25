package com.example.y_trackcomercial.ui.menuPrincipal

import RutasAccesos
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
 import com.example.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MenuPrincipalViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _rol = mutableStateOf("")
    val rol: State<String> = _rol

    private val _rutas = sharedPreferences.getRutasAccesos() //mutableStateListOf<RutasAccesos>()//
    val rutas: List<RutasAccesos> = _rutas







    fun getUserName(): String = sharedPreferences.getUserName().toString()
    fun getRol(): String = sharedPreferences.getRol().toString()

    fun getRutasAccesos():List<RutasAccesos> = sharedPreferences.getRutasAccesos()

}