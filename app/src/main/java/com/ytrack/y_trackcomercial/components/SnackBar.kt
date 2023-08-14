package com.ytrack.y_trackcomercial.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
 import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay

@Composable
fun SnackAlerta(mensaje:String?,colorSnack:Color) {
    var snackbarVisible by remember { mutableStateOf(true) }
    // Creamos un valor animado para controlar la opacidad del SnackBar
    val animatedAlpha = animateFloatAsState(
        targetValue = if (snackbarVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
    )
    // Ejecutar LaunchedEffect cada vez que snackbarVisible cambie a true
    LaunchedEffect(snackbarVisible) {
        if (snackbarVisible) {
            // Mostrar el SnackBar durante 5 segundos
            delay(5000)
            // Ocultar el SnackBar después de 5 segundos
            snackbarVisible = false
        }
    }
    if (snackbarVisible) {
        // Contenido del SnackBar con la animación de opacidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent), // Transparent background to cover the whole screen
            contentAlignment = Alignment.Center // Align the Snackbar to the top center of the screen
        ) {
            Snackbar(
                modifier = Modifier

                    .padding(16.dp)
                    .alpha(animatedAlpha.value) ,
                backgroundColor = colorSnack ,
                contentColor = Color.White,
                action = {
                    // Acción en el botón del SnackBar (opcional)
                    TextButton(onClick = {
                        snackbarVisible = false
                    }) {
                        Text("Cerrar", fontSize = 14.sp)
                    }
                }
            ) {
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Warning, // Icono de alerta de Material Design
                        contentDescription = "Alerta",
                        tint = Color.White
                    )
                    Text(mensaje!!, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

    }
}
