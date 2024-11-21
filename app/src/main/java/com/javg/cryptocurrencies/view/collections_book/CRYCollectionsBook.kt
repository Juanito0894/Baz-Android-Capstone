package com.javg.cryptocurrencies.view.collections_book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enums.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.model.CRYCardItemBuilder
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.view.components.CRYCardBookUI
import com.javg.cryptocurrencies.view.components.CRYContentBooksUI
import com.javg.cryptocurrencies.view.components.CRYTopHeaderBarUI
import com.javg.cryptocurrencies.view.theme.Neutral
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.myTypography
import com.javg.cryptocurrencies.view.viewmodel.CRYCollectionsBooksVM

@Composable
fun CRYCollectionsBookScreen(acronym: String, onClickBack: () -> Unit){
    val collectionBooksVM: CRYCollectionsBooksVM = hiltViewModel()
    val book by collectionBooksVM.generalBook.observeAsState()

    LaunchedEffect(key1 = true) {
        collectionBooksVM.findGeneralBook(acronym)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(Primary500)) {

        val topBarBuilder = CRYTopHeaderBuilder()
            .withTypeHeader(CRYEnumsTopBar.NORMAL)
            .withTitle(book?.fullName.orEmpty())
            .withOnClick { onClickBack() }

        CRYTopHeaderBarUI(topBarBuilder)
        Row(Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = R.drawable.ic_change_coins),
                contentDescription = "",
                tint = Neutral, modifier = Modifier.padding(top = 4.dp))

            Spacer(Modifier.width(16.dp))

            Text(
                text = stringResource(id = R.string.cry_have_conversions),
                style = myTypography.h1,
                fontWeight = FontWeight.Normal,
                color = Neutral)
        }
        CRYContentBooksUI {
            book?.conversions?.let { conversions ->
                if (conversions.isNotEmpty()) {
                    LazyColumn {
                        items(conversions){
                            CRYCardBookUI(CRYCardItemBuilder()
                                .withTitle(it.acronym.toUpperCase())
                                .withTextMoney(it.maximumAmount)
                                .withTextSubtitleMoney(it.rate)
                                .withIconId(it.imageId))
                        }
                    }
                }
            }
        }
    }
}