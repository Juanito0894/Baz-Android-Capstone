package com.javg.cryptocurrencies.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enums.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeCard
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeFlow
import com.javg.cryptocurrencies.data.model.CRYCardItemBuilder
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.utils.shimmerBackground
import com.javg.cryptocurrencies.view.components.CRYCardBookUI
import com.javg.cryptocurrencies.view.components.CRYContentBooksUI
import com.javg.cryptocurrencies.view.components.CRYErrorScreen
import com.javg.cryptocurrencies.view.components.CRYTopHeaderBarUI
import com.javg.cryptocurrencies.view.theme.Primary200
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.Text1
import com.javg.cryptocurrencies.view.theme.Text2
import com.javg.cryptocurrencies.view.theme.myTypography
import com.javg.cryptocurrencies.view.theme.robotoSlabFamily
import com.javg.cryptocurrencies.view.viewmodel.CRYHomeVM

@Composable
fun CRYDashboardBooksScreen(
    homeVM: CRYHomeVM,
    onClick: (typeFlow: CRYEnumsTypeFlow, acronym: String) -> Unit){

    val response by homeVM.stateGeneralBooks.collectAsState()
    val topHeaderBuilder = CRYTopHeaderBuilder()
        .withTypeHeader(CRYEnumsTopBar.TEXT_ONLY)
        .withTitle("Crytopbook")

    Column(
        Modifier
            .fillMaxSize()
            .background(Primary500)) {
        CRYTopHeaderBarUI(topHeaderBuilder)
        when(response) {
            is CRYDataState.Loading -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)) {
                    repeat(11){
                        CRYSkeletonBook()
                    }
                }
            }
            is CRYDataState.Success -> {
                CRYSearchBook{ value ->
                    homeVM.searchBook(value)
                }
                CRYContentBooksUI{
                    LazyColumn {
                        items((response as CRYDataState.Success<List<CRYGeneralBook>>).data){
                            CRYCardBookUI(
                                cryCardItemBuilder = CRYCardItemBuilder()
                                    .withTitle(it.fullName)
                                    .withSubtitle(it.acronym)
                                    .withIconId(it.logoId)
                                    .withButtonAction(true)
                                    .withHaveCollections(it.conversions.size > 1)
                                    .withTypeCard(CRYEnumsTypeCard.DASHBOARD)
                                    .withOnClick {
                                        onClick(homeVM.getTypeView(it.conversions), homeVM.getIdBook(it)) })
                        }
                    }
                }
            }
            is CRYDataState.Error -> CRYErrorScreen(
                title = stringResource(id = R.string.cry_internet_error_title),
                nameBtn = "Volver a intentar",
                message = (response as CRYDataState.Error).message){
                homeVM.getBooks()
            }
            else -> {}
        }
    }
}

@Composable
fun CRYSearchBook(onValue: (String) -> Unit){
    val currentContext = LocalContext.current
    var searchString by remember { mutableStateOf("") }

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
                    onValue(it)
                },
                modifier = Modifier
                    .height(24.dp),
                textStyle = TextStyle(
                    color = Text1
                ),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(Primary200),
                        verticalAlignment = Alignment.CenterVertically,) {

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

@Composable
fun CRYSkeletonBook(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically){

        Spacer(modifier = Modifier
            .size(30.dp)
            .clip(RoundedCornerShape(20.dp))
            .shimmerBackground(RoundedCornerShape(2.dp)))
        Spacer(Modifier.width(20.dp))
        Column(Modifier.weight(3f)) {
            Spacer(modifier = Modifier
                .height(16.dp)
                .width(100.dp)
                .shimmerBackground(RoundedCornerShape(2.dp)))
            Spacer(Modifier.height(4.dp))
            Spacer(modifier = Modifier
                .height(12.dp)
                .width(60.dp)
                .shimmerBackground(RoundedCornerShape(2.dp)))
        }
        Spacer(modifier = Modifier
            .padding(bottom = 16.dp)
            .height(20.dp)
            .width(80.dp)
            .shimmerBackground(RoundedCornerShape(2.dp))
            .align(Alignment.Bottom))
    }
}


