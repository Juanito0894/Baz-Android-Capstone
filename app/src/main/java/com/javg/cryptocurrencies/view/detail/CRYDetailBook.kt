package com.javg.cryptocurrencies.view.detail

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enums.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeList
import com.javg.cryptocurrencies.data.model.CRYCardInformationBookBuilder
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.utils.formatAmount
import com.javg.cryptocurrencies.utils.shimmerBackground
import com.javg.cryptocurrencies.view.components.CRYCardInformationBookUI
import com.javg.cryptocurrencies.view.components.CRYContentBooksUI
import com.javg.cryptocurrencies.view.components.CRYErrorScreen
import com.javg.cryptocurrencies.view.components.CRYTopHeaderBarUI
import com.javg.cryptocurrencies.view.theme.Background1
import com.javg.cryptocurrencies.view.theme.Neutral
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.Text1
import com.javg.cryptocurrencies.view.theme.myTypography
import com.javg.cryptocurrencies.view.theme.robotoSlabFamily
import com.javg.cryptocurrencies.view.viewmodel.CRYDetailBookVM

@Composable
fun CRYDetailBookScreen(acronym: String, onClickBack: () -> Unit) {
    val detailVM: CRYDetailBookVM = hiltViewModel()
    val response by detailVM.responseDetailBook.collectAsState()
    val fullName by detailVM.fullName.collectAsState()
    val informationBooks by detailVM.listAskOrBids.collectAsState()

    LaunchedEffect(key1 = true) {
        Log.e("CRYDetailBookScreen","-------book -> $acronym")
        detailVM.getTicker(acronym.split("|").last())
        detailVM.getInfoBook(acronym.split("|").first())
    }
    val topBarBuilder = CRYTopHeaderBuilder()
        .withTitle(fullName)
        .withTypeHeader(CRYEnumsTopBar.NORMAL)
        .withOnClick { onClickBack() }

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

        when(response) {
            is CRYDataState.Loading -> {
                CRYSkeletonDetail()
            }
            is CRYDataState.Success -> {
                Column(
                    Modifier
                        .animateContentSize()
                        .fillMaxWidth()
                        .height(animatedDpState),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.cry_last_price),
                        fontFamily = robotoSlabFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        color = Neutral
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = (response as CRYDataState.Success<CRYDetailBook>).data.last.formatAmount(),
                        style = myTypography.h1,
                        color = Neutral
                    )
                    Spacer(Modifier.height(18.dp))
                    Row {
                        CRYSectionInfoMoney(text = "Más bajo", price = (response as CRYDataState.Success<CRYDetailBook>).data.low, idIcon = R.drawable.ic_arrow_down)
                        CRYSectionInfoMoney(text = "Más alto", price = (response as CRYDataState.Success<CRYDetailBook>).data.high, idIcon = R.drawable.ic_arrow_up)
                    }
                    Spacer(Modifier.height(12.dp))
                }
                CRYSectionButton(
                    onClickButtonLeft = {
                        detailVM.updateInformationBooks(CRYEnumsTypeList.ASK)
                    }){
                    detailVM.updateInformationBooks(CRYEnumsTypeList.BIDS)
                }
                CRYContentBooksUI {
                    LazyColumn(
                        state = state,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(informationBooks){
                            Spacer(Modifier.height(16.dp))
                            CRYCardInformationBookUI(
                                cryCardInformationBookBuilder = CRYCardInformationBookBuilder()
                                    .withPrice(it.price.formatAmount())
                                    .withAmount(it.amount.formatAmount())
                            )
                        }
                    }
                }
            }
            is CRYDataState.Error -> {
                CRYErrorScreen(
                    title = stringResource(id = R.string.cry_internet_error_title),
                    message = (response as CRYDataState.Error).message,
                    nameBtn = "Reintentar"){
                    detailVM.getTicker(acronym.split("|").last())
                }
            }
            else -> {}
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
fun CRYSectionInfoMoney(text: String, price: String, idIcon: Int) {
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
                painter = painterResource(id = idIcon),
                contentDescription = "",
                tint = Neutral)
            Spacer(Modifier.width(16.dp))
            Text(text = text,
                style = myTypography.h3,
                fontWeight = FontWeight.Medium,
                color = Neutral)
        }
        Text(
            text = price.formatAmount(),
            style = myTypography.body1,
            color = Neutral)
    }
}

@Composable
fun CRYSkeletonDetail(){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier
            .width(200.dp)
            .height(40.dp)
            .shimmerBackground(RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier
            .width(300.dp)
            .height(40.dp)
            .shimmerBackground(RoundedCornerShape(2.dp)))
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.padding(horizontal = 40.dp, vertical = 0.dp)) {
            Spacer(modifier = Modifier
                .height(60.dp)
                .weight(1f)
                .shimmerBackground(RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(56.dp))
            Spacer(modifier = Modifier
                .height(60.dp)
                .weight(1f)
                .shimmerBackground(RoundedCornerShape(2.dp)))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(Modifier.padding(horizontal = 24.dp, vertical = 0.dp)) {
            Spacer(modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .shimmerBackground(RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Spacer(modifier = Modifier
                .height(40.dp)
                .weight(1f)
                .shimmerBackground(RoundedCornerShape(2.dp)))
        }
        Spacer(modifier = Modifier.height(16.dp))
        repeat(6){
            Spacer(modifier = Modifier
                .width(340.dp)
                .height(35.dp)
                .shimmerBackground(RoundedCornerShape(2.dp)))
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}