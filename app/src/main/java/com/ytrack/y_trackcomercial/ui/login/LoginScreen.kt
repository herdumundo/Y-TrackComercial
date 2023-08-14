package com.ytrack.y_trackcomercial.ui.login2

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ytrack.y_trackcomercial.components.RoundedButton
import com.ytrack.y_trackcomercial.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {
    val botonCargar by loginViewModel.botonCargar.observeAsState(1)
    val gradientColors: List<Color> = listOf(Color(0xFF920505), Color(0xFF000000))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(brush = Brush.verticalGradient(colors = gradientColors)),
         ) {
             Header(Modifier.align(Alignment.TopEnd))
            Body(Modifier.align(Alignment.Center), loginViewModel, botonCargar, navController)
            Spacer(modifier = Modifier.size(60.dp))
            Footer(Modifier.align(Alignment.BottomCenter))
        }
}

@Composable
fun LoginButton(
    loginViewModel: LoginViewModel,
    botonCargar: Int,
    navController: NavController,
) {
    val loading by loginViewModel.loading.observeAsState(initial = false)
    val usuario by loginViewModel.userName.observeAsState(initial = "")
    val pass by loginViewModel.password.observeAsState(initial = "")
    val mensajeBoton by loginViewModel.mensajeBoton.observeAsState(initial = "Iniciar sesión")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RoundedButton(
            text = if (usuario.isEmpty() or pass.isEmpty()) "Ingrese datos" else mensajeBoton,
            displayProgressBar = loading,
            onClick = {
                loginViewModel.autenticacionLogin {

                    navController.navigate("menu")
                }
            },
            enabled = usuario.isNotEmpty() && pass.isNotEmpty(),
            backgroundColor = if (botonCargar == 4) Color(0xFFf910000) else Color(0xFF4B4B4D)
        )

    }
}



@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close,
        contentDescription = "close app",
        tint = Color.White,
        modifier = modifier
            .clickable { activity.finish() }
            .padding(10.dp))
}

@Composable
fun Body(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    botonCargar: Int,
    navController: NavController,
) {
    val userName by loginViewModel.userName.observeAsState(initial = "")
    val password by loginViewModel.password.observeAsState(initial = "")

    Column(modifier = modifier) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))

        Box( ){
            Column(modifier = modifier) {
                UserName(userName) { loginViewModel.onLoginChanged(userName = it, password = password) }
                Spacer(modifier = Modifier.size(4.dp))
                Password(password) { loginViewModel.onLoginChanged(userName = userName, password = it) }

            }
           }

        Spacer(modifier = Modifier.size(8.dp))
  //      NoHazSincronizado(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(loginViewModel, botonCargar, navController)
        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.size(32.dp))
     }


}

@Composable
fun Footer(modifier: Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        /*   Divider(
               Modifier
                   .background(Color(0xFFF9F9F9))
                   .height(1.dp)
                   .fillMaxWidth()
           )*/
        Spacer(modifier = Modifier.size(24.dp))
        LoginDivider()
        Spacer(modifier = Modifier.size(24.dp))
    }
}

@Composable
fun LoginDivider() {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
        Text(
            text = "Y TRACK 1.0",
            modifier = Modifier.padding(horizontal = 18.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB5B5B5)
        )
        Divider(
            Modifier
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
                .weight(1f)
        )
    }
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.ytrack2), contentDescription = "Logo"
        )
    }
}

@Composable
fun UserName(userName: String, onTextChanged: (String) -> Unit) {

    OutlinedTextField(
        value = userName,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        label = { Text("Usuario") },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color(0xFFFFFFFF),
            unfocusedLabelColor = Color(0xFFFFFFFF),
            focusedBorderColor = Color(0xFFFFFFFF),
            unfocusedBorderColor = Color(0xFFFFFFFF),
            textColor = Color(0xFFFFFFFF)

        ),
        singleLine = true

    )

}

@Composable
fun Password(password: String, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Contraseña") },
        singleLine = true,
        maxLines = 1,

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val imagen = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = imagen, contentDescription = "show password", tint = Color.White)
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color(0xFFFFFFFF),
            focusedBorderColor = Color(0xFFFFFFFF),
            unfocusedLabelColor = Color(0xFFFFFFFF),
            unfocusedBorderColor = Color(0xFFFFFFFF),
            textColor = Color(0xFFFFFFFF)
        )
    )
}
/*
@Composable
fun NoHazSincronizado(modifier: Modifier) {
    Text(
        text = "No haz sincronizado?",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EA8E9),
        modifier = modifier
    )
}*/