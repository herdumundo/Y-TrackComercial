package com.portalgm.y_trackcomercial.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
                        .padding(end =1.dp),
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
            .clickable {   },
        backgroundColor = color,
        contentColor = Color(0xFFFFF8F8),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end =1.dp),
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