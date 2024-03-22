package com.portalgm.y_trackcomercial.ui.facturacion.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.portalgm.y_trackcomercial.ui.facturacion.viewmodel.OinvViewModel

@Composable
fun FacturaScreen(oinvViewModel: OinvViewModel) {
       Column(
        modifier = Modifier
            .padding(16.dp),
        // .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {oinvViewModel.getLista()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF135202)),
            modifier = Modifier.fillMaxWidth() // Ancho máximo para que ocupe todo el espacio
        ) {
            Text(
                "Factura",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFFFFFFF)
            )
        }

    }
}
