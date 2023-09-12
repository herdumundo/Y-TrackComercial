package com.portalgm.y_trackcomercial.data.api

import android.util.Log
import com.portalgm.y_trackcomercial.data.api.apiResponse.AuthClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(private val authClient: AuthClient) {
      suspend fun iniciarSesionServices(user: String, password: String): UsuarioResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = authClient.iniciarSesion(user, password, "ANDROID")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    // Si el JSON contiene el campo "Sesion"
                    if (responseBody?.Sesion == true) {
                        responseBody
                    } else {
                        responseBody
                    }
                } else {
                    // Manejar la respuesta no exitosa si es necesario
                    null
                }
            } catch (e: Exception) {
                // Manejar excepciones de red u otras excepciones
                Log.i("MensajeiniciarSesionServices",e.toString())
                null
            }
        }
    }
}