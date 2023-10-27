package com.portalgm.y_trackcomercial.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight


@Composable
fun MyBottomBar(prepareBottomMenu: List<BottomMenuItem>) {
    // items list
    val bottomMenuItemsList = prepareBottomMenu


    var selectedItem by remember {
        mutableStateOf("Home")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BottomNavigation(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            contentColor = Color(0xFFFFFFFF),// Establecer el color de fondo a negro
           backgroundColor = Color(0xFF000000)// Establecer el color de fondo a negro

        ) {
                  bottomMenuItemsList.forEach { menuItem ->
                // adding each item
                BottomNavigationItem(
                    selected = (selectedItem == menuItem.label),
                    onClick = {
                        selectedItem = menuItem.label
                        menuItem.onClickAction() },
                    icon = {
                        Icon(
                            imageVector = menuItem.icon,
                            contentDescription = menuItem.label
                        )
                    },
                    label = {
                        Text(text = menuItem.label,
                            fontWeight = FontWeight.Bold // Hacer que el texto esté en negrita
                        )
                    },
                    enabled = true
                )
            }
        }
    }
}

data class BottomMenuItem(val label: String, val icon: ImageVector, val onClickAction: () -> Unit)
