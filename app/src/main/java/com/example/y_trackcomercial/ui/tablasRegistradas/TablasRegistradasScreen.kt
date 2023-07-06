package com.example.y_trackcomercial.ui.tablasRegistradas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.y_trackcomercial.R


@Composable
fun ScreenTablasRegistradas(tablasRegistradasViewModel: TablasRegistradasViewModel) {
    val ocrdCount by tablasRegistradasViewModel.ocrdCount.observeAsState()
    val ubicacionesCount by tablasRegistradasViewModel.ocrdUbicacionesCount.observeAsState()
    val rutasAccesosCount by tablasRegistradasViewModel.rutasAccesoCount.observeAsState()
    val lotesListasCount by tablasRegistradasViewModel.lotesListasCount.observeAsState()
    val horariosUsuarioCount by tablasRegistradasViewModel.horariosUsuarioCount.observeAsState()
    val auditTrailCount by tablasRegistradasViewModel.auditTrailCount.observeAsState()

    LaunchedEffect(Unit) {
        tablasRegistradasViewModel.getCustomerCount()
    }
    Column {
        cardViewRegistrosTablas(
            title = "Clientes cargados",
            subTitle = ocrdCount.toString(),
            image = R.drawable.ic_clientes
        )
        cardViewRegistrosTablas(
            title = "Ubicaciones cargadas",
            subTitle = ubicacionesCount.toString(),
            image = R.drawable.ic_map
        )
        cardViewRegistrosTablas(
            title = "Permisos otorgados",
            subTitle = rutasAccesosCount.toString(),
            image = R.drawable.ic_permisos
        )
        cardViewRegistrosTablas(
            title = "Lotes cargados",
            subTitle = lotesListasCount.toString(),
            image = R.drawable.ic_lotes
        )
        cardViewRegistrosTablas(
            title = "Horarios cargados",
            subTitle = horariosUsuarioCount.toString(),
            image = R.drawable.ic_horario
        )

        cardViewRegistrosTablas(
            title = "Auditoria de camino",
            subTitle = auditTrailCount.toString(),
            image = R.drawable.ic_step
        )
    }
}


@Composable
fun cardViewRegistrosTablas(
    title: String, subTitle: String, image: Int
) {
    AnimatedVisibility(
        visible = true, // Puedes cambiar esta condición según tus necesidades
        enter = fadeIn(), exit = fadeOut()
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.error,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                    .padding(10.dp),
                ) {

                    Column(
                        modifier = Modifier
                            .weight(2f)
                            .padding(end = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            title,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            subTitle,
                            modifier = Modifier.padding(5.dp),
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(id = image),
                        tint = Color(0xFFFFFFFF),
                        contentDescription = null
                    )
                }
            })
    }


}

