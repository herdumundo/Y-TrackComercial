package com.example.y_trackcomercial.ui.login

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.y_trackcomercial.R

@Composable
fun LoginButton(loginViewModel: LoginViewModel, botonCargar: Boolean) {
    val usuario  by loginViewModel.userName.observeAsState(initial = "")
    val pass  by loginViewModel.password.observeAsState(initial = "")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                 .fillMaxWidth()
                .height(45.dp).border(BorderStroke(1.dp, Color(0xFF4B4B4D))), shape = RoundedCornerShape(0.dp),
            onClick = { loginViewModel.onLoginSelected();/* loading = true */},
            colors = ButtonDefaults.buttonColors(
                containerColor =   if (botonCargar)  Color(0xFF910000) else  Color(0xFF4B4B4D),
                contentColor =    Color(0xFFFFFFFF) ,
                disabledContainerColor = Color(0xFFE0C77B),
                disabledContentColor = Color(0xFF857171),
            ),
            enabled = usuario.isNotEmpty() && pass.isNotEmpty()

        ) {
            Text(
                text =  if( usuario.isEmpty() or pass.isEmpty())  "Ingrese datos" else if (botonCargar) "Iniciando..." else "Iniciar sesión",

                fontSize = 15.sp
            )
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequiredFieldExample() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var error by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                error = it.text.isEmpty() // Validamos si el campo está vacío
            },
            label = { Text("Nombre") },
            placeholder = { Text("Escriba su nombre") },
            modifier = Modifier.padding(16.dp),
            isError = error // Mostramos el error si el campo está vacío
        )

        Button(
            onClick = { /* Acción al pulsar el botón */ },
            enabled = name.text.isNotEmpty() // Desactivamos el botón si el campo está vacío
        ) {
            Text("Enviar")
        }
    }
}








@Composable
fun LoginScreen2(loginViewModel: LoginViewModel) {

    val isLoggedIn  by loginViewModel.loggedIn.observeAsState(initial = false)
    val botonCargar by loginViewModel.botonCargar.observeAsState(initial = false)
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState()) // <-- Agregado
    ) {
        Header(Modifier.align(Alignment.TopEnd))
        Body(Modifier.align(Alignment.Center), loginViewModel,botonCargar)
        Footer(Modifier.align(Alignment.BottomCenter))

    }
}


@Composable
fun UnregisteredUserDialog(loginViewModel: LoginViewModel) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = "Usuario no registrado",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = "Por favor, regístrese para continuar",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { loginViewModel.onDialogClose() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Blue),
                content = {
                    Text(text = "OK", color = Color.White)
                }
            )
        } ,
        shape = MaterialTheme.shapes.medium,
    )
}
@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close,
        contentDescription = "close app",
        modifier = modifier.clickable { activity.finish() })
}


@Composable
fun Body(modifier: Modifier, loginViewModel: LoginViewModel, botonCargar: Boolean) {
    val userName by loginViewModel.userName.observeAsState(initial = "")
    val password by loginViewModel.password.observeAsState(initial = "")

    Column(modifier = modifier) {
        ImageLogo(Modifier.align(Alignment.CenterHorizontally))
        UserName(userName) { loginViewModel.onLoginChanged(userName = it, password = password) }
        Spacer(modifier = Modifier.size(4.dp))
        Password(password) { loginViewModel.onLoginChanged(userName = userName, password = it) }
        Spacer(modifier = Modifier.size(8.dp))
        NoHazSincronizado(Modifier.align(Alignment.End))
        Spacer(modifier = Modifier.size(16.dp))
        LoginButton(loginViewModel,botonCargar)
        Spacer(modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.size(32.dp))
        //  SocialLogin()
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


@OptIn(ExperimentalMaterial3Api::class)
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
            focusedLabelColor = Color(0xFFCE0303),
            unfocusedLabelColor = Color(0xFF4B4B4D),
            focusedBorderColor = Color(0xFFCE0303),
            unfocusedBorderColor = Color(0xFF4B4B4D)
        ),
        singleLine = true

    )

}


@OptIn(ExperimentalMaterial3Api::class)
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
                Icon(imageVector = imagen, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color(0xFFCE0303),
            focusedBorderColor = Color(0xFFCE0303),
            unfocusedLabelColor = Color(0xFF4B4B4D),
            unfocusedBorderColor = Color(0xFF4B4B4D)
        )
    )
}


@Composable
fun NoHazSincronizado(modifier: Modifier) {
    Text(
        text = "No haz sincronizado?",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4EA8E9),
        modifier = modifier
    )
}


