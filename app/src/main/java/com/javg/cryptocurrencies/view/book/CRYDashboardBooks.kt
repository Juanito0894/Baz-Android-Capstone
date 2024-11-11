package com.javg.cryptocurrencies.view.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enum.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.model.CRYCardItemBuilder
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.view.components.CRYCardBookUI
import com.javg.cryptocurrencies.view.components.CRYContentBooksUI
import com.javg.cryptocurrencies.view.components.CRYTopHeaderBarUI
import com.javg.cryptocurrencies.view.theme.Error
import com.javg.cryptocurrencies.view.theme.Primary200
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.Text1
import com.javg.cryptocurrencies.view.theme.Text2
import com.javg.cryptocurrencies.view.theme.myTypography
import com.javg.cryptocurrencies.view.theme.robotoSlabFamily

@Composable
fun CRYDashboardBooksScreen(onClick: () -> Unit){
    val topHeaderBuilder = CRYTopHeaderBuilder()
        .withTypeHeader(CRYEnumsTopBar.TEXT_ONLY)
        .withTitle("Crytopbook")

    val cardItemBuilder = CRYCardItemBuilder()
        .withTitle("Litecoin")
        .withSubtitle("LTC")
        .withIconId(R.drawable.ic_litecoin)
        .withButtonAction(true)
        .withOnClick { onClick() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Primary500)) {
        CRYTopHeaderBarUI(topHeaderBuilder)
        CRYSearchBook()
        CRYContentBooksUI{
            Column {
                repeat(5){
                    CRYCardBookUI(cardItemBuilder)
                }
            }
        }
    }
}

@Preview
@Composable
fun CRYSearchBook(){
    val currentContext = LocalContext.current
    var searchString by remember {
        mutableStateOf("")
    }

    Card(
        Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(5.dp)) {

        Row(Modifier
            .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = currentContext.getString(R.string.cry_coins_list),
                style = myTypography.body1,
                color = Text1)

            Spacer(Modifier.width(8.dp))

            BasicTextField(
                value = searchString,
                onValueChange = {
                    searchString = it
                },
                modifier = Modifier.height(24.dp),
                textStyle = TextStyle(
                    color = Error
                ),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary200),
                        verticalAlignment = Alignment.CenterVertically) {
                        if (searchString.isEmpty()) {
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Buscar moneda",
                                fontFamily = robotoSlabFamily,
                                fontWeight = FontWeight.Medium,
                                color = Text2 ,
                                fontSize = 12.sp
                            )
                        }
                    }
                    innerTextField()
                }
            )
        }
    }
}


