package com.portalgm.y_trackcomercial.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.portalgm.y_trackcomercial.R

@Composable
fun cardViewLoadingTablas(
    textoLoading: String,
    title: String,
    subTitle: String,
    image: Int,
    isLoading: Boolean,
    color: Color,
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
        }
    )
}


@Composable
fun cardViewToolBar(
    title: String,
    icon: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
        backgroundColor = color,
        contentColor = Color(0xFFFFF8F8),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(size = 24.dp),
                    imageVector = icon.toIcon(),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    )
}


@Composable
fun cardView2Title(
    title1: String,
    title2: String,
    title3: String,
    title4: String,
    icon: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { },
        backgroundColor = color,
        contentColor = Color(0xFFFFF8F8),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title1,
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        title2,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        title3,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        title4,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(size = 24.dp),
                    imageVector = icon.toIcon(),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    )
}


@Composable
fun CardOrdenVenta(
    title1: String,
    title2: String,
    title3: String,
    buttonText: String,
    icono: ImageVector,
    isAnulado: Boolean ,// Agrega un parámetro para controlar si se muestra el ribbon de "Anulado"
    isImpresion: Boolean ,// Agrega un parámetro para controlar si se muestra el ribbon de "Anulado"
    fondoColor:Color,
    onClick: () -> Unit
) {
    Box {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 5.dp,

            color = fondoColor//
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title1,
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title2,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (!isAnulado||isImpresion) {

                        Button(
                            shape = RoundedCornerShape(4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
                            onClick = { onClick() }
                        ) {
                            Row {
                                Icon(
                                    imageVector = icono,
                                    contentDescription = "Print",
                                    tint = Color.White
                                )
                                Text(
                                    text = buttonText,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        Ribbon(ribbonNumber = title3)
        if (isAnulado) {
            AnuladoRibbon(Modifier.align(Alignment.TopEnd))
        }
    }
}


@Composable
fun CardOrdenVenta2(
    title1: String,
    title2: String,
    title3: String,
    buttonText: String,
    icono: ImageVector,
    isAnulado: Boolean, // Controlar si se muestra el ribbon de "Anulado"
    isImpresion: Boolean, // Controlar si se muestra el ribbon de "Anulado"
    fondoColor: Color,
    onClick: () -> Unit,
) {
    Box {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 5.dp, // Usar elevation en lugar de shadowElevation
            color = fondoColor
        ) {
            Box {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = R.drawable.launcher), // Reemplaza con tu recurso de imagen
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    alpha = 0.1f // Ajustar la transparencia de la marca de agua
                )

                // Contenido de la tarjeta
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = title1,
                        style = androidx.compose.material3.MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (!isAnulado || isImpresion) {
                            Button(
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E0400)),
                                onClick = { onClick() }
                            ) {
                                Row {
                                    Icon(
                                        imageVector = icono,
                                        contentDescription = "Print",
                                        tint = Color.White
                                    )
                                    Text(
                                        text = buttonText,
                                        color = Color.White,
                                        modifier = Modifier.padding(start = 8.dp),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if(title3.length>1){
            Ribbon2(ribbonNumber = title2)
        }

    }
}


@Composable
fun Ribbon2(ribbonNumber: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = Color(0xFF9E0400),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    bottomEnd = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .padding(
                PaddingValues(
                    horizontal = 8.dp,
                    vertical = 2.dp
                )
            ) // Ajusta el padding para el tamaño del texto
    ) {
        Text(
            text = ribbonNumber,
            color = Color.White,
            fontSize = 20.sp, // Ajusta el tamaño del texto si es necesario
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun Ribbon(ribbonNumber: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = Color(0xFF9E0400),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    bottomEnd = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .padding(
                PaddingValues(
                    horizontal = 8.dp,
                    vertical = 2.dp
                )
            ) // Ajusta el padding para el tamaño del texto
    ) {
        Text(
            text = ribbonNumber,
            color = Color.White,
            fontSize = 12.sp, // Ajusta el tamaño del texto si es necesario
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AnuladoRibbon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .graphicsLayer {
                // Establecer la rotación y el punto de pivote para rotar el contenedor completo
                rotationZ = 30f
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0.1f,
                    pivotFractionY = 0.1f
                ) // Ajustar el punto de pivote según sea necesario
            }
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    bottomEnd = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .background(Color.Red)
            .padding(horizontal = 10.dp, vertical = 4.dp)
            .width(80.dp) // Establece un ancho fijo para el ribbon
    ) {
        Text(
            text = "ANULADO",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.Center) // Alinea el texto en el centro del ribbon rotado
        )
    }
}
