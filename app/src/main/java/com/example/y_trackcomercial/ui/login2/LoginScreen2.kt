package com.example.y_trackcomercial.ui.login2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
 import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import coil.compose.rememberAsyncImagePainter
import com.example.y_trackcomercial.R

@Composable
fun LogButton(  // 1
    text: String,
    onClick: () -> Unit,
) {
    Column(  // 2
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(  // 3
            onClick = { onClick() },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
        ) {
            Text(  // 4
                text = text,
                fontSize = 20.sp,
            )
        }
    }
}



@Composable
fun MainView() {
    var userIsAuthenticated by remember { mutableStateOf(false) }
    var appJustLaunched by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        /// 👇🏽👇🏽👇🏽 1. Updated code
        // Title
        // -----
        val title = if (userIsAuthenticated) {
            stringResource(R.string.logged_in_title)
        } else {
            if (appJustLaunched) {
                stringResource(R.string.initial_title)
            } else {
                stringResource(R.string.logged_out_title)
            }
        }
        Text(
            text = title
        )
        /// 👆🏽👆🏽👆🏽
        /// 👇🏽👇🏽👇🏽 2. New code
        // User info
        // ---------
        if (userIsAuthenticated) {
            /// 👆🏽👆🏽👆🏽
            UserInfoRow(
                label = stringResource(R.string.name_label),
                value = "Name goes here",
            )
            UserInfoRow(
                label = stringResource(R.string.email_label),
                value = "Email goes here",
            )
            UserPicture(
                url = stringResource(R.string.user_icon_url),
                description = "Description goes here",
            )
            /// 👇🏽👇🏽👇🏽 3. New code
        }
        /// 👆🏽👆🏽👆🏽
        /// 👇🏽👇🏽👇🏽 4. New code
        // Button
        // ------
        val buttonText: String
        val onClickAction: () -> Unit
        if (userIsAuthenticated) {
            buttonText = stringResource(R.string.log_out_button)
            onClickAction = {
                userIsAuthenticated = false
                appJustLaunched = false
            }
        } else {
            buttonText = stringResource(R.string.log_in_button)
            onClickAction = {
                userIsAuthenticated = true
            }
        }
        /// 👆🏽👆🏽👆🏽
        LogButton(
            /// 👇🏽👇🏽👇🏽 5. Updated code
            text = buttonText,
            onClick = onClickAction,
            /// 👆🏽👆🏽👆🏽
        )
    }
}

@Composable
fun UserInfoRow(
    label: String,
    value: String,
) {
    Row() {  // 1
        Text(
            text = label,
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        )
        Spacer( // 2
            modifier = Modifier.width(10.dp),
        )
        Text(
            text = value,
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = FontFamily.Default,
                fontSize = 20.sp,
            )
        )
    }
}



@Composable
fun UserPicture(  // 1
    url: String,
    description: String,
) {
    Column(  // 2
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(  // 3
            painter = rememberAsyncImagePainter(url),
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize(0.5f),
        )
    }
}