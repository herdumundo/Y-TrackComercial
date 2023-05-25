package com.example.y_trackcomercial.ui.menuPrincipal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.components.CustomDialog
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuPrincipal(
    loginViewModel: LoginViewModel,
    navControllerPrincipal: NavController,
    menuPrincipalViewModel: MenuPrincipalViewModel
) {

    if (loginViewModel.loggedIn.value == true) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var expanded by rememberSaveable { mutableStateOf(false) }
        val navController = rememberNavController()
        val openDialogCustom = remember{ mutableStateOf(false) }
        val activity = LocalContext.current as Activity


         Scaffold(
            topBar = {
                MyTopAppBar(
                    onNavIconClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    },
                    expanded = expanded,
                    onMoreOptionsClick = { expanded = !expanded },
                    menuPrincipalViewModel,
                    openDialogCustom,
                    activity
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                MyDrawer(
                    //menu_items = navigationItems,
                    coroutineScope,
                    scaffoldState,
                    navController,
                    menuPrincipalViewModel
                )
            },

            ) {

            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen()
                }
                composable("registroNuevo") {
                    RegistroNuevo(menuPrincipalViewModel)
                }

            }
             if (openDialogCustom.value) {

                 CustomDialog(openDialogCustom = openDialogCustom) { activity.finish() }
             }
        }
    } else {
        navControllerPrincipal.navigate("login")

    }

}


private fun openWhatsApp(context: Context, menuPrincipalViewModel: MenuPrincipalViewModel) {  // on below line we are starting activity

    context.startActivity(
        // on below line we are opening the intent.
        Intent(
            // on below line we are calling
            // uri to parse the data
            Intent.ACTION_VIEW,
            Uri.parse(
                // on below line we are passing uri,
                // message and whats app phone number.
                java.lang.String.format(  "https://api.whatsapp.com/send?phone=%s&text=%s",
                    "595961873186",
                    "Buen dia, soy ${menuPrincipalViewModel.getUserName()}, necesito soporta para "
                )
            )
        )
    )
}


@Composable
fun MyTopAppBar(
    onNavIconClick: () -> Unit,
    expanded: Boolean,
    onMoreOptionsClick: () -> Unit,
    menuPrincipalViewModel: MenuPrincipalViewModel,
    openDialogCustom: MutableState<Boolean>,
    activity: Activity,

    ) {

    TopAppBar(
        backgroundColor = Color(0xFFCE0303),
        contentColor = Color.White,

        navigationIcon = {
            IconButton(
                onClick = {
                    onNavIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Drawer",
                    tint = Color.White
                )
            }
        },

        title = {
            Text(
                text = "Y TRACK",
                color = Color.White,
                fontSize = 20.sp
            )
        },
        actions = {
            IconButton(onClick = {
                openWhatsApp(activity,menuPrincipalViewModel)
            }) {
                //painter = painterResource(id = androidx.compose.foundation.layout.R.drawable.img_what),
                Image(
                    painter = painterResource(id =  R.drawable.img_what),
                    contentDescription = "Image",
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(5.dp)
                        .clip(CircleShape),

                )
            }

            IconButton(
                onClick = { onMoreOptionsClick() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {   onMoreOptionsClick()}
            ) {

                DropdownMenuItem(
                    onClick = {
                        onMoreOptionsClick();
                    }
                ) {
                    Text(text = "Sincronizar datos")
                }

                DropdownMenuItem(
                    onClick = {
                        openDialogCustom.value = true
                        onMoreOptionsClick();
                        /*  activity.finish()*/
                    }

                ) {
                    Text(text = "Cerrar sesión")

                }
            }
        }
    )
}


@SuppressLint("SuspiciousIndentation")
@Composable

fun MyDrawer(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    menuPrincipalViewModel: MenuPrincipalViewModel
) {
    val rol = menuPrincipalViewModel.getRol()
    val userName = menuPrincipalViewModel.getUserName()
    val acesso = menuPrincipalViewModel.getRutasAccesos()


    val gradientColors: List<Color> = listOf(Color(0xFFf910000), Color(0xFF090808))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        //   contentPadding = PaddingValues(vertical = 36.dp)
    ) {
        // user's image
        Image(
            modifier = Modifier
                .size(size = 120.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.ytrack2),
            contentDescription = "Profile Image"
        )

        Text(
            modifier = Modifier
                .padding(top = 12.dp),
            text = userName ,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        // user's email
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
            text = rol,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White
        )
        Divider(
            Modifier
                .background(Color(0xFFEBE2E2))
                .height(1.dp)
                .fillMaxWidth()
        )

        acesso.forEach { rutaAcceso ->
            val icono = rutaAcceso.icono  // Obtener el icono correspondiente
            val nombre = rutaAcceso.name
            val ruta = rutaAcceso.ruta

            DrawerItem(icono, nombre, ruta, coroutineScope, scaffoldState, navController)
        }
    }
}

@Composable
fun DrawerItem(
    icono: String,
    name: String,
    ruta: String,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController
) {
           Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(6.dp)
                .clip(RoundedCornerShape(12))
                .padding(8.dp)
                .clickable {
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(ruta)
                },
        )
        {
            Box {
                Icon(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(size = 24.dp),
                    imageVector = icono.toIcon(),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Text(text = name, style = MaterialTheme.typography.body1, color = Color.White)
        }
}

@Composable
fun String.toIcon(): ImageVector =
    when (this) {
        "Login" -> Icons.Filled.Login
        "RegistroNuevo" -> Icons.Filled.PersonAdd
        else -> Icons.Filled.Login
    }

@Composable
fun HomeScreen() {
    Text(text = "hola mundo", style = MaterialTheme.typography.body1, color = Color.Black)

}


@Composable
fun RegistroNuevo(menuPrincipalViewModel: MenuPrincipalViewModel) {

    Text(
        text = "ESTE ES UN REGISTRO DE ASDASDASD",
        style = MaterialTheme.typography.body1,
        color = Color.Black
    )
    
    Button(onClick = {   }) {
        Text(text = "PROBAR")
        
    }
}









