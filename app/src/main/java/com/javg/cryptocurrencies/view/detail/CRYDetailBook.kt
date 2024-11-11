package com.javg.cryptocurrencies.view.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enum.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.utils.formatMoney
import com.javg.cryptocurrencies.view.components.CRYContentBooksUI
import com.javg.cryptocurrencies.view.components.CRYTopHeaderBarUI
import com.javg.cryptocurrencies.view.theme.Background1
import com.javg.cryptocurrencies.view.theme.Neutral
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.Text1
import com.javg.cryptocurrencies.view.theme.myTypography
import com.javg.cryptocurrencies.view.theme.robotoSlabFamily

@Preview
@Composable
fun CRYDetailBookScreen() {
    val currentContext = LocalContext.current
    val topBarBuilder = CRYTopHeaderBuilder()
        .withTitle("Bitcoin")
        .withTypeHeader(CRYEnumsTopBar.NORMAL)
        .withOnClick { }

    val elements = (1..100).map { "Item $it" }
    val state = rememberLazyListState()
    var expanded by remember {
        mutableStateOf(true)
    }
    val animatedDpState by animateDpAsState(
        if (expanded) 154.dp else 0.dp,
        animationSpec = tween(1300),
        label = "")
    var toDoMax by remember {
        mutableStateOf(true)
    }

    var toDoMin by remember {
        mutableStateOf(false)
    }

    if (toDoMax){
        if (remember { derivedStateOf { state.firstVisibleItemIndex } }.value > 0){
            expanded = !expanded
            toDoMax = false
            toDoMin = true
        }
    }
    if (toDoMin){
        if (remember { derivedStateOf { state.firstVisibleItemIndex } }.value < 1){
            expanded = !expanded
            toDoMax = true
            toDoMin = false
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Primary500)
    ) {

        CRYTopHeaderBarUI(topHeaderBuilder = topBarBuilder)
        Column(
            Modifier
                .animateContentSize()
                .fillMaxWidth()
                .height(animatedDpState),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = currentContext.getString(R.string.cry_last_price),
                fontFamily = robotoSlabFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Neutral
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "410060.00".formatMoney(),
                style = myTypography.h1,
                color = Neutral
            )
            Spacer(Modifier.height(18.dp))
            Row {
                CRYSectionInfoMoney()
                CRYSectionInfoMoney()
            }
            Spacer(Modifier.height(12.dp))
        }
        CRYSectionButton(
            onClickButtonLeft = {
                Toast.makeText(currentContext, "Click button left.", Toast.LENGTH_SHORT).show()
            }){
            Toast.makeText(currentContext, "Click button right.", Toast.LENGTH_SHORT).show()
        }
        CRYContentBooksUI {
            LazyColumn(
                state = state,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(elements){
                    Spacer(Modifier.height(16.dp))
                    Text(text = "Aqui podemos ver un $it.", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun CRYSectionButton(onClickButtonLeft: ()-> Unit, onClickButtonRight: ()-> Unit) {
    var backgroundColorEnabled by remember { mutableStateOf(Background1) }
    var contentColorEnabled by remember { mutableStateOf(Text1) }
    var backgroundColorDisable by remember { mutableStateOf(Primary500) }
    var contentColorDisable by remember { mutableStateOf(Neutral) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Button(
            onClick = {
                backgroundColorEnabled = Background1
                contentColorEnabled = Text1
                backgroundColorDisable = Primary500
                contentColorDisable = Neutral
                onClickButtonLeft()
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColorEnabled,
                contentColor = contentColorEnabled
            ),
            border = BorderStroke(width = 2.dp, color = Background1)
        ) {
            Text(
                text = stringResource(id = R.string.cry_txt_ask),
                style = myTypography.body1)
        }
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = {
                backgroundColorDisable = Background1
                contentColorDisable = Text1
                backgroundColorEnabled = Primary500
                contentColorEnabled = Neutral
                onClickButtonRight()
            },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColorDisable,
                contentColor = contentColorDisable
            ),
            border = BorderStroke(width = 2.dp, color = Background1)
        ) {
            Text(
                text = stringResource(id = R.string.cry_txt_bids),
                style = myTypography.body1)
        }
    }
}

@Composable
fun CRYSectionInfoMoney() {
    Column(
        Modifier
            .background(Primary500)
            .padding(
                horizontal = 40.dp,
                vertical = 4.dp
            ),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "",
                tint = Neutral)
            Spacer(Modifier.width(16.dp))
            Text(text = "Más bajo",
                style = myTypography.h3,
                fontWeight = FontWeight.Medium,
                color = Neutral)
        }
        Text(
            text = "0.00001331".formatMoney(),
            style = myTypography.body1,
            color = Neutral)
    }
}