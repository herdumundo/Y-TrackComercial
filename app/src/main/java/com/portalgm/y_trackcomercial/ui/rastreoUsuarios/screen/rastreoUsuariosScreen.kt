package com.portalgm.y_trackcomercial.ui.rastreoUsuarios.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.portalgm.y_trackcomercial.ui.rastreoUsuarios.viewmodel.RastreoUsuariosViewModel


@Composable
fun RastreoUsuarioMapa(rastreoUsuariosViewModel: RastreoUsuariosViewModel) {

    val Paraguay = LatLng(-25.3608, -57.5562)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Paraguay, 10f)}
    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier. height(500.dp) // La composable ocupará el 50% de la pantalla en altura
                // Asegura que la composable solo tome el espacio necesario en altura
                .padding(16.dp), // Agrega un padding opcional
            cameraPositionState = cameraPositionState
        ) {

            Marker(
                state = MarkerState(position = LatLng(-25.3208872, -57.5942957)),
                title = "RETAIL S.A. - STOCK - RCA. ARGENTINA",
                snippet = "1"

            )
            MarkerInfoWindowContent(
                state = MarkerState(position = LatLng(-25.324815, -57.57229)),
                title = "SUPER BOX S.A."
            ) { marker ->
                Text(marker.title ?: "Default Marker Title", color = Color.Red)
            }

            MarkerInfoWindowContent(
                state = MarkerState(position = LatLng(-25.2874838747084, -57.6379837375134)),
                title = "RETAIL S.A. - STOCK - MALL EXCELSIOR"     ,
                snippet = "1",
 
                )
            { marker ->
                // Implement the custom info window here
                Column {
                    Text(marker.title ?: "Default Marker Title", color = Color.Red)
                    Text(marker.snippet ?: "Default Marker Snippet", color = Color.Red)
                }
            }
        }

    }
}