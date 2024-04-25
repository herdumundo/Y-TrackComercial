package com.portalgm.y_trackcomercial.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddHomeWork
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun String.toIcon(): ImageVector = when (this) {
    "Login" -> Icons.Filled.Login
    "ContentPaste" -> Icons.Filled.ContentPaste
    "ContentCopy" -> Icons.Filled.ContentCopy
    "Inventory" -> Icons.Filled.Inventory
    "Create" -> Icons.Filled.Create
    "Calculate" -> Icons.Filled.Calculate
    "Clear" -> Icons.Filled.Clear
    "AddCircle" -> Icons.Filled.AddCircle
    "Send" -> Icons.Filled.Send
    "Save" -> Icons.Filled.Save
    "ContentPasteSearch" -> Icons.Filled.ContentPasteSearch
    "WatchLater" -> Icons.Filled.WatchLater
    "SaveAlt" -> Icons.Filled.SaveAlt
    "Dashboard" -> Icons.Filled.Dashboard
    "Cloud" -> Icons.Filled.Cloud
    "Fingerprint" -> Icons.Filled.Fingerprint
    "Assistant" -> Icons.Filled.Assistant
    "AppRegistration" -> Icons.Filled.AppRegistration
    "Print" -> Icons.Filled.Print
    "Receipt" -> Icons.Filled.ReceiptLong
    "AccountBox" -> Icons.Filled.AccountBox
    "Alarm" -> Icons.Filled.Alarm
    "BatteryChargingFull" -> Icons.Filled.BatteryChargingFull
    "Camera" -> Icons.Filled.Camera
    "Dining" -> Icons.Filled.Dining
    "Edit" -> Icons.Filled.Edit
    "Email" -> Icons.Filled.Email
    "Favorite" -> Icons.Filled.Favorite
    "Group" -> Icons.Filled.Group
    "Home" -> Icons.Filled.Home
    "Info" -> Icons.Filled.Info
    "Key" -> Icons.Filled.Key
    "Lock" -> Icons.Filled.Lock
    "Map" -> Icons.Filled.Map
    "Navigation" -> Icons.Filled.Navigation
    "Phone" -> Icons.Filled.Phone
    "School" -> Icons.Filled.School
    "ShoppingCart" -> Icons.Filled.ShoppingCart
    "Timer" -> Icons.Filled.Timer
    "Visibility" -> Icons.Filled.Visibility
    "Work" -> Icons.Filled.Work
    "ZoomIn" -> Icons.Filled.ZoomIn
    // Agrega más íconos según lo necesites
    else -> Icons.Filled.Error // Un ícono predeterminado para cuando no hay coincidencias
}
