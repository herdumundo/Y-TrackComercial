package com.portalgm.y_trackcomercial.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
      /*  // Crea una corrutina para esperar un tiempo antes de pasar a la siguiente actividad
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
        }*/
    }


    @Composable
    fun SplashScreen() {
        val alpha= remember {
             Animatable(0f)
        }
        LaunchedEffect(key1 = true  ){
            alpha.animateTo(1f, animationSpec = tween(1500))
            delay(2000)
            if(!sharedPreferences.getProminent()!!){
                val intent = Intent(this@SplashActivity, avisoProminentActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
        // Puedes personalizar el diseño de tu splash screen aquí
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {

                Image(

                    painter = painterResource(R.drawable.ytrack2),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp).alpha(alpha.value),
                )

            }
        }
    }
}
