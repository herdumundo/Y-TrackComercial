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
import javax.inject.Inject

@AndroidEntryPoint

class avisoProminentActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Muestra el splash screen usando Jetpack Compose
        setContent {

            Surface(color = Color(0xFF000000)) {
                InfoDialogDosBoton(
                    title = "Acceso a la Ubicación",
                    titleBottom = "Aceptar",
                    desc = "La aplicación Y-TRACK COMERCIAL utiliza la ubicación en segundo plano para proporcionar funciones de seguimiento y geolocalización incluso cuando la aplicación está cerrada. Esto nos permite mejorar tu experiencia y brindarte un servicio más eficiente. Tus datos de ubicación nunca se compartirán con terceros. A continuación, se muestran las características que utilizan la ubicación en segundo plano: [Captura de ubicacion cada 50 metros.].",
                    R.drawable.icono_exit ,
                    {
                        sharedPreferences.saveProminent(
                            true
                        )
                        val intent = Intent(this@avisoProminentActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    {
                        finish()
                    }
                )

            }
        }
    }
}