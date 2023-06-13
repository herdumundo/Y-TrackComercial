package com.example.y_trackcomercial.ui.menuPrincipal

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.y_trackcomercial.R
import com.example.y_trackcomercial.components.DialogLoading
import com.example.y_trackcomercial.components.InfoDialog
import com.example.y_trackcomercial.components.InfoDialogOk
import com.example.y_trackcomercial.model.entities.OCRDEntity
import com.example.y_trackcomercial.ui.login2.LoginViewModel
import com.example.y_trackcomercial.ui.tablasRegistradas.ScreenTablasRegistradas
import com.example.y_trackcomercial.ui.tablasRegistradas.TablasRegistradasViewModel
//import com.example.y_trackcomercial.ui.registroEntradaPromotoras.cargar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MenuPrincipal(
    loginViewModel: LoginViewModel,
    navControllerPrincipal: NavController,
    menuPrincipalViewModel: MenuPrincipalViewModel,
    tablasRegistradasViewModel: TablasRegistradasViewModel
) {

    if (loginViewModel.loggedIn.value == true) {
        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        var expanded by rememberSaveable { mutableStateOf(false) }
        val navController = rememberNavController()
        val openDialogSesion = remember { mutableStateOf(false) }
        val openDialogSincro = remember { mutableStateOf(false) }
        val activity = LocalContext.current as Activity
        val showDialog by menuPrincipalViewModel.showLoading.observeAsState(initial = false)
        val showDialogOK by menuPrincipalViewModel.showLoadingOk.observeAsState(initial = false)
        val mensajeDialog by menuPrincipalViewModel.mensajeDialog.observeAsState("")

       // cargar()
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
                    openDialogSesion,
                    openDialogSincro,
                    activity
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                MyDrawer(
                    //menu_items = navigationItems,
                    coroutineScope, scaffoldState, navController, menuPrincipalViewModel
                )
            },

            )
        {

            DialogLoading(mensajeDialog, showDialog)


            if (openDialogSesion.value) {

                // CustomDialog(openDialogCustom = openDialogSincro,"¿Deseas actualizar los datos del telefono?.",R.drawable.ic_sincro) { menuPrincipalViewModel.getOCRD() }

                InfoDialog(title = "Atenciòn!",
                    desc = "¿Deseas cerrar sesión?.",
                    R.drawable.icono_exit,
                    { activity.finish() },
                    onDismiss = {
                        openDialogSesion.value = false
                    })
            }

            if (openDialogSincro.value) {
                InfoDialog(title = "Atenciòn!",
                    desc = "¿Deseas actualizar los datos del telefono?.",
                    R.drawable.icono_sincro,
                    { menuPrincipalViewModel.getOCRD() },
                    {
                        openDialogSincro.value = false
                    })

            }

            if (showDialogOK) {
                InfoDialogOk(title = "Atenciòn!",
                    desc = mensajeDialog,
                    R.drawable.icono_sincro,
                    { },
                    {
                        menuPrincipalViewModel.cerrarAviso()
                    })
            }

                   NavHost(navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen()
                    }
                    composable("registroNuevo") {
                        ScreenTablasRegistradas(tablasRegistradasViewModel)
                    }
                }

        }
    } else {
        navControllerPrincipal.navigate("login")
    }
}


@Composable
fun CircularProgressBar(progress: Float) {
    val animatedProgress by animateFloatAsState(targetValue = progress)

    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.size(64.dp)
        )
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun MyUI(menuPrincipalViewModel: MenuPrincipalViewModel) {
    // progress value
//    var progress by remember {   mutableStateOf(0f)   }
    var progress = 0f//by menuPrincipalViewModel.progress.observeAsState(0f)
    // CircularProgressIndicator(progress = progress.toFloat())

    // animation
    val progressAnimate by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    CircularProgressIndicator(
        progress = progressAnimate,
        modifier = Modifier.size(size = 64.dp),
        color = Color.Magenta,
        strokeWidth = 6.dp
    )

    Spacer(modifier = Modifier.height(height = 16.dp))

    Button(
        onClick = {
            progress += 1f
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Magenta
        )
    ) {
        Text(
            text = "Random",
            color = Color.White
        )
    }
}

private fun openWhatsApp(
    context: Context, menuPrincipalViewModel: MenuPrincipalViewModel
) {
    context.startActivity(
        // on below line we are opening the intent.
        Intent(
            // on below line we are calling
            // uri to parse the data
            Intent.ACTION_VIEW, Uri.parse(
                // on below line we are passing uri,
                // message and whats app phone number.
                java.lang.String.format(
                    "https://api.whatsapp.com/send?phone=%s&text=%s",
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
    openDialogSincro: MutableState<Boolean>,
    activity: Activity,

    ) {

    TopAppBar(backgroundColor = Color(0xFFCE0303), contentColor = Color.White,

        navigationIcon = {
            IconButton(onClick = {
                onNavIconClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Drawer",
                    tint = Color.White
                )
            }
        },

        title = {
            Text(
                text = "Y TRACK", color = Color.White, fontSize = 20.sp
            )
        }, actions = {
            IconButton(onClick = {
                openWhatsApp(activity, menuPrincipalViewModel)
            }) {
                //painter = painterResource(id = androidx.compose.foundation.layout.R.drawable.img_what),
                Image(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "Image",
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                )
            }

            IconButton(
                onClick = { onMoreOptionsClick() }, modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More Options",
                    tint = Color.White
                )
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { onMoreOptionsClick() }) {

                DropdownMenuItem(onClick = {
                    openDialogSincro.value = true
                    onMoreOptionsClick();
                }) {
                    Text(text = "Sincronizar datos")
                }

                DropdownMenuItem(onClick = {
                    openDialogCustom.value = true
                    onMoreOptionsClick();/*  activity.finish()*/
                }

                ) {
                    Text(text = "Cerrar sesión")

                }
            }
        })
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
    //val acesso = menuPrincipalViewModel.getRutasAccesos()
    val acesso = menuPrincipalViewModel.rutas.value ?: emptyList()


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
            modifier = Modifier.padding(top = 12.dp),
            text = userName,
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
    ) {
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
fun String.toIcon(): ImageVector = when (this) {
    "Login" -> Icons.Filled.Login
    "RegistroNuevo" -> Icons.Filled.PersonAdd
    else -> Icons.Filled.Login
}

@Composable
fun HomeScreen() {
    Text(text = "hola mundo", style = MaterialTheme.typography.body1, color = Color.Black)

}

/*
@Composable
fun RegistroNuevo(menuPrincipalViewModel: MenuPrincipalViewModel) {
     val customers by menuPrincipalViewModel.customers.observeAsState(emptyList())

// Obtiene los clientes cuando se realiza la llamada a la API
    LaunchedEffect(menuPrincipalViewModel.customers) {
        menuPrincipalViewModel.getOCRD()
    }
    LazyColumn  {
        items(customers) { OCRD  ->
            CustomerItem(OCRD)
        }
    }

}*/
@Composable
fun CustomerItem(customer: OCRDEntity) {
    // Item de la lista para cada cliente
    Text(text = customer.Address)
    Text(text = customer.CardName)
    Divider()
}








