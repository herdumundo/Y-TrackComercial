package com.example.y_trackcomercial.ui.tablasRegistradas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import com.example.y_trackcomercial.ui.menuPrincipal.MenuPrincipalViewModel


@Composable
fun ScreenTablasRegistradas(
    tablasRegistradasViewModel: TablasRegistradasViewModel,
    menuPrincipalViewModel: MenuPrincipalViewModel
) {
    val ocrdCount by tablasRegistradasViewModel.ocrdCount.observeAsState()
    val ubicacionesCount by tablasRegistradasViewModel.ocrdUbicacionesCount.observeAsState()
    val rutasAccesosCount by tablasRegistradasViewModel.rutasAccesoCount.observeAsState()
    val lotesListasCount by tablasRegistradasViewModel.lotesListasCount.observeAsState()
    val horariosUsuarioCount by tablasRegistradasViewModel.horariosUsuarioCount.observeAsState()
    val auditTrailCount by tablasRegistradasViewModel.auditTrailCount.observeAsState()
    val oitmCount by tablasRegistradasViewModel.oitmCount.observeAsState()
    val ocrdOitmCount by tablasRegistradasViewModel.ocrdOitmCount.observeAsState()
    val ubicacionesPvCount by tablasRegistradasViewModel.ubicacionesPvCount.observeAsState()
    val permisoVisitaCount by tablasRegistradasViewModel.permisoVisitaCount.observeAsState()

    val LoadingOcrdCount by tablasRegistradasViewModel.loadingOcrdCount.observeAsState(false)
    val  LoadingUbicacionesCount by tablasRegistradasViewModel.loadingUbicacionesCount.observeAsState(false)
    val  LoadingRutasAccesosCount by tablasRegistradasViewModel.loadingRutasAccesosCount.observeAsState(false)
    val  LoadingLotesListasCount by tablasRegistradasViewModel.loadingLotesListasCount.observeAsState(false)
    val  LoadingHorariosUsuarioCount by tablasRegistradasViewModel.loadingHorariosUsuarioCount.observeAsState(false)
     val LoadingOitmCount by tablasRegistradasViewModel.loadingOitmCount.observeAsState(false)
    val  LoadingOcrdOitmCount by tablasRegistradasViewModel.loadingOcrdOitmCount.observeAsState(false)
    val  LoadingUbicacionesPvCount by tablasRegistradasViewModel.loadingUbicacionesPvCount.observeAsState(false)
    val  loadingpermisoVisitaCount by tablasRegistradasViewModel.loadingpermisoVisitaCount.observeAsState(false)
    val colorCard=Color(0xFFB90000)


    LaunchedEffect(Unit) {
        tablasRegistradasViewModel.getTablasRegistradas()
    }


    LazyColumn {
        item {

            cardViewLoadingTablas(
                textoLoading = "Obteniendo permisos...", title = "Permisos de horarios",color = colorCard, subTitle = permisoVisitaCount.toString(),
                image = R.drawable.ic_clock_permiso, isLoading = loadingpermisoVisitaCount
            ) { tablasRegistradasViewModel.actualizarDatos(9)}

        }
        item {

            cardViewLoadingTablas(
                textoLoading = "Actualizando Clientes...",color = colorCard, title = "Clientes cargados", subTitle = ocrdCount.toString(),
                image = R.drawable.ic_clientes, isLoading = LoadingOcrdCount
            ) { tablasRegistradasViewModel.actualizarDatos(1)}

        }
        item {

            cardViewLoadingTablas(
                textoLoading = "Actualizando ubicaciones...",color = colorCard, title = "Ubicaciones cargadas",
                subTitle = ubicacionesCount.toString(),
                image = R.drawable.ic_map,
                isLoading = LoadingUbicacionesCount
            ) {  tablasRegistradasViewModel.actualizarDatos(2)}
        }
        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando permisos otorgados...",color =  Color(0xFF090808), title = "Permisos otorgados",
                subTitle = rutasAccesosCount.toString(),
                image = R.drawable.ic_permisos,
                isLoading = LoadingRutasAccesosCount
            ) { tablasRegistradasViewModel.actualizarDatos(3) }
        }
        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando lotes disponibles...",color =  colorCard, title = "Lotes disponibles",
                subTitle = lotesListasCount.toString(),
                image = R.drawable.ic_lotes, isLoading = LoadingLotesListasCount
            ) { tablasRegistradasViewModel.actualizarDatos(4) }
        }
        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando horarios cargados...",color =  colorCard, title = "Horarios cargados",
                subTitle = horariosUsuarioCount.toString(),
                image = R.drawable.ic_horario, isLoading = LoadingHorariosUsuarioCount
            ) {  tablasRegistradasViewModel.actualizarDatos(5)}
        }
        item {

            cardViewLoadingTablas(
                textoLoading = "", title = "Auditoria de camino",color =  colorCard,
                subTitle = auditTrailCount.toString(),
                image = R.drawable.ic_step, isLoading = false
            ) { }
        }

        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando productos disponibles...",color =  colorCard, title = "Productos disponibles",
                subTitle = oitmCount.toString(),
                image = R.drawable.ic_products, isLoading = LoadingOitmCount
            ) {  tablasRegistradasViewModel.actualizarDatos(6)}
        }

        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando productos por punto venta...",color =colorCard, title = "Productos por punto venta",
                subTitle = ocrdOitmCount.toString(),
                image = R.drawable.ic_products, isLoading = LoadingOcrdOitmCount
            ) { tablasRegistradasViewModel.actualizarDatos(7) }
        }

        item {
            cardViewLoadingTablas(
                textoLoading = "Actualizando depositos ...",color = colorCard, title = "Depositos en punto de venta",
                subTitle = ubicacionesPvCount.toString(),
                image = R.drawable.ic_lugar, isLoading = LoadingUbicacionesPvCount
            ) { tablasRegistradasViewModel.actualizarDatos(8) }
        }
    }

}




@Composable
fun cardViewLoadingTablas(
    textoLoading: String,
    title: String,
    subTitle: String,
    image: Int,
    isLoading: Boolean,
    color:Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        backgroundColor = color,
        contentColor = Color(0xFFFFF8F8),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                CircularProgressIndicator()
                                Text(
                                    text = textoLoading,
                                    style = MaterialTheme.typography.body1
                                )
                            }
                        }
                    } else {
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
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = image),
                    tint = Color(0xFFFFFFFF),
                    contentDescription = null
                )
            }

            // Agregar el mensaje de "Cargando" y ProgressBar dentro de un Box

        }
    )
}
