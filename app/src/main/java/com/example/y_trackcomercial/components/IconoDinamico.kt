package com.example.y_trackcomercial.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AddHomeWork
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Assistant
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


    @Composable
    fun String.toIcon(): ImageVector = when (this) {
        "Login" -> Icons.Filled.Login
        "ContentPaste" -> Icons.Filled.ContentPaste
        "ContentCopy" -> Icons.Filled.ContentCopy
        "Inventory" -> Icons.Filled.Inventory
        "Inventory2" -> Icons.Filled.Inventory2
        "Create" -> Icons.Filled.Create
        "Calculate" -> Icons.Filled.Calculate
        "Clear" -> Icons.Filled.Clear
        "AddCircle" -> Icons.Filled.AddCircle
        "Send" -> Icons.Filled.Send
        "Save" -> Icons.Filled.Save
        "ContentPasteSearch" -> Icons.Filled.ContentPasteSearch
        "SaveAlt" -> Icons.Filled.SaveAlt
        "AddHomeWork" -> Icons.Filled.AddHomeWork
        "Dashboard" -> Icons.Filled.Dashboard
        "Cloud" -> Icons.Filled.Cloud
        "Fingerprint" -> Icons.Filled.Fingerprint
        "Assistant" -> Icons.Filled.Assistant
        "AppRegistration" -> Icons.Filled.AppRegistration
         else -> Icons.Filled.AppRegistration
    }
