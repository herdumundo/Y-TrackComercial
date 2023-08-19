package com.ytrack.y_trackcomercial.ui.theme

 import androidx.compose.material.Typography
 import androidx.compose.ui.text.TextStyle
 import androidx.compose.ui.text.font.Font
 import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
 import com.ytrack.y_trackcomercial.R
/*
val quickSandFontFamily = FontFamily(
    Font(R.font.quicksand_regular, FontWeight.Normal),
    Font(R.font.quicksand_bold, FontWeight.Bold)
)*/

// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)



val tags = Typography.body1.copy(  fontWeight = FontWeight.Bold)

val bodyBold = Typography.body1.copy(fontWeight = FontWeight.Bold)

val accordionHeaderStyle = Typography.h6.copy(fontWeight = FontWeight.Bold)