package com.portalgm.y_trackcomercial.ui.splash

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.portalgm.y_trackcomercial.R
import com.portalgm.y_trackcomercial.components.InfoDialogDosBoton
import com.portalgm.y_trackcomercial.services.system.ServicioUnderground

@Composable
fun SplashScreen() {
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
                modifier = Modifier.size(200.dp)
            )

        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SplashScreen()
    }
}