package com.portalgm.y_trackcomercial.ui.cambioPass.screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.portalgm.y_trackcomercial.components.SnackAlerta
import com.portalgm.y_trackcomercial.ui.cambioPass.viewmodel.CambioPassViewModel

@Composable
    fun CambioPassScreen(cambioPassViewModel: CambioPassViewModel,    onDismiss: () -> Unit,
) {
    val currentPassword by cambioPassViewModel.currentPassword.observeAsState(initial = "")
    val newPassword by cambioPassViewModel.newPassword.observeAsState(initial = "")
    val confirmNewPassword by cambioPassViewModel.confirmNewPassword.observeAsState(initial = "")
    val mensajeBotonRegistrar by cambioPassViewModel.mensajeAlerta.observeAsState(initial = "Cambiar Contraseña")

    Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

         ) {

            OutlinedTextField(
                modifier= Modifier.fillMaxWidth(),
                value = newPassword,
                onValueChange = { cambioPassViewModel.onNewPassChanged( it)},
                label = { Text("Nueva contraseña") },
              //  visualTransformation = PasswordVisualTransformation(),
            )

            OutlinedTextField(
                modifier= Modifier.fillMaxWidth(),
                value = confirmNewPassword,
                onValueChange = { cambioPassViewModel.onConfirmNewPassChanged( it)  },
                label = { Text("Confirmar nueva contraseña") },
              //  visualTransformation = PasswordVisualTransformation(),
            )
                 Button(
                    onClick = {
                        // Aquí debes implementar la lógica para cambiar la contraseña
                        if (newPassword == confirmNewPassword) {

                            cambioPassViewModel.insertarDatos()
                            onDismiss()
                        } else {
                            cambioPassViewModel.errorDatos()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF135202)),
                    modifier = Modifier.fillMaxWidth() // Ancho máximo para que ocupe todo el espacio
                ) {
                    Text(
                        mensajeBotonRegistrar,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFFFFFFF)
                    )
                }
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCE0303)),
                    modifier = Modifier.fillMaxWidth() // Ancho máximo para que ocupe todo el espacio
                ) {
                    Text(
                        text = "Cancelar",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFFFFFFF)
                    )
                }
         }


     }






@Composable
fun InfoDialogNewPass(
    image: Int,
    onDismiss: () -> Unit,
    cambioPassViewModel: CambioPassViewModel
) {
    val alerta by cambioPassViewModel.alerta.observeAsState(false)
    val mensajeAlerta by cambioPassViewModel.mensajeAlerta.observeAsState("")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFDF7F7),
                        shape = RoundedCornerShape(20.dp, 20.dp, 50.dp, 50.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(175.dp)

                        .fillMaxWidth(),
                )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = "Cambio de contraseña",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    CambioPassScreen(cambioPassViewModel = cambioPassViewModel,onDismiss)

                  }
            }
        }
    }
   /* if(alerta){
        SnackAlerta(mensajeAlerta,Color(0xFF161010))
        Spacer(modifier = Modifier.height(25.dp))
    }*/
}