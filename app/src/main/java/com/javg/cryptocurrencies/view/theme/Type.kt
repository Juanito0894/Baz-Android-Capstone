package com.javg.cryptocurrencies.view.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.javg.cryptocurrencies.R

val robotoSlabFamily = FontFamily(
    Font(R.font.roboto_slab_bold, FontWeight.Bold),
    Font(R.font.roboto_slab_medium, FontWeight.Medium),
    Font(R.font.roboto_slab_regular, FontWeight.Normal)
)
// Set of Material typography styles to start with
val myTypography = Typography(
    h1 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h2 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp
    ),
    h3 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    body1 = TextStyle(
        fontFamily = robotoSlabFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)