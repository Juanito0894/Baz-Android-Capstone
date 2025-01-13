package com.javg.cryptocurrencies.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Function that extends a string to be able to separate the text by a '_'
 * symbol and return the first element separated in it
 */
fun String.getFirstCoinsText() = this.split("_").first()

/**
 * function that extends a string to get the
 * second element separated by the '_' symbol
 */
fun String.getSecondCoinsText(): String {
    val list = this.split("_")
    return if (list.size >= 2) list[1] else this
}

fun String.formatMoney(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 12.sp)){
            append("$")
        }
        append(this@formatMoney)
    }
}

fun String.formatAmount(): String {
    val symbols = DecimalFormatSymbols(Locale.US)
    val formatter = DecimalFormat("###,###,###.########", symbols)
    val convertAmount = formatter.format(this.toDouble())
    return String.format(Locale.US, "$%s", convertAmount)
}
