package com.portalgm.y_trackcomercial.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import com.portalgm.y_trackcomercial.MainActivity
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.InfoDialogDosBoton
import com.portalgm.y_trackcomercial.util.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Muestra el splash screen usando Jetpack Compose
        setContent {

            Surface(color = Color(0xFF000000)) {
                SplashScreen()
            }
        }
        // Crea una corrutina para esperar un tiempo antes de pasar a la siguiente actividad
        CoroutineScope(Dispatchers.Main).launch {
            // Tiempo en milisegundos que mostrará el splash screen (por ejemplo, 2 segundos)
            delay(2000)
            // Inicia la siguiente actividad después del tiempo de espera

           if(!sharedPreferences.getProminent()!!){
               val intent = Intent(this@SplashActivity, avisoProminentActivity::class.java)
               startActivity(intent)
           }
            else{
               val intent = Intent(this@SplashActivity, MainActivity::class.java)
               startActivity(intent)
           }

            finish() // Finaliza la actividad actual para que no se pueda volver atrás
        }
    }
}
