package com.ytrack.y_trackcomercial.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    displayProgressBar: Boolean = false,
    onClick: () -> Unit,
    enabled:Boolean,
    backgroundColor:Color
) {
    if(!displayProgressBar) {
        Button(
            modifier = modifier.fillMaxWidth()
                .height(45.dp)
                .border(BorderStroke(1.dp, Color(0xFF4B4B4D))), shape = RoundedCornerShape(0.dp),
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor    =   backgroundColor ,
                contentColor =    Color(0xFFFFFFFF) ,
                disabledBackgroundColor = Color(0xFF9E9D9B),
                disabledContentColor = Color(0xFF857171),
            ),
            //shape = RoundedCornerShape(50),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
        }
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = Color.White,
            strokeWidth = 6.dp
        )
    }
}
