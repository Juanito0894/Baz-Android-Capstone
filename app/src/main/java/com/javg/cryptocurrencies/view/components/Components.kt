package com.javg.cryptocurrencies.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.enum.CRYEnumsTopBar
import com.javg.cryptocurrencies.data.model.CRYCardItemBuilder
import com.javg.cryptocurrencies.data.model.CRYTopHeaderBuilder
import com.javg.cryptocurrencies.utils.formatMoney
import com.javg.cryptocurrencies.view.theme.Action
import com.javg.cryptocurrencies.view.theme.Background1
import com.javg.cryptocurrencies.view.theme.Neutral
import com.javg.cryptocurrencies.view.theme.Primary200
import com.javg.cryptocurrencies.view.theme.Primary500
import com.javg.cryptocurrencies.view.theme.Success
import com.javg.cryptocurrencies.view.theme.Text1
import com.javg.cryptocurrencies.view.theme.Text2
import com.javg.cryptocurrencies.view.theme.myTypography

@Composable
fun CRYTopHeaderBarUI(topHeaderBuilder: CRYTopHeaderBuilder){
    Box(
        Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(Primary500),
        contentAlignment = Alignment.Center) {

        when (topHeaderBuilder.getType()) {
            CRYEnumsTopBar.TEXT_ONLY -> {
                Text(
                    text = topHeaderBuilder.getTitle(),
                    color = Neutral,
                    style = myTypography.h1,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(16.dp))
            }
            CRYEnumsTopBar.NORMAL -> {
                topHeaderBuilder.getOnClick()?.let {
                    IconButton(
                        onClick = it,
                        modifier = Modifier.align(Alignment.CenterStart)) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "")
                    }
                }
                Text(
                    text = topHeaderBuilder.getTitle(),
                    color = Neutral,
                    style = myTypography.h1)
            }
        }
    }
}

@Composable
fun CRYCardBookUI(cryCardItemBuilder: CRYCardItemBuilder){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .background(Background1)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {

            if (cryCardItemBuilder.getIconId() != 0) {
                Image(
                    painter = painterResource(id = cryCardItemBuilder.getIconId()),
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp))
            }

            Spacer(Modifier.width(20.dp))
            Column(Modifier.weight(3f)) {
                if (cryCardItemBuilder.getTitle().isNotEmpty()) {
                    Text(text = cryCardItemBuilder.getTitle(),
                        style = myTypography.h3,
                        fontWeight = FontWeight.Medium,
                        color = Text1)
                }

                if (cryCardItemBuilder.getSubtitle().isNotEmpty()) {
                    Text(text = cryCardItemBuilder.getSubtitle(),
                        style = myTypography.subtitle1,
                        color = Text2)
                }
            }
            if (cryCardItemBuilder.getButtonAction()) {
                TextButton(onClick = cryCardItemBuilder.getOnClick(),
                    modifier = Modifier.align(Alignment.Bottom)) {
                    Text(text = "Ver todos",
                        style = myTypography.h4,
                        color = Action)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                if (cryCardItemBuilder.getTextMoney().isNotEmpty()) {
                    Text(text = cryCardItemBuilder.getTextMoney().formatMoney(),
                        style = myTypography.h3,
                        fontWeight = FontWeight.Medium,
                        color = Text1)
                }

                if (cryCardItemBuilder.getTextSubtitleMoney().isNotEmpty()) {
                    Text(text = cryCardItemBuilder.getTextSubtitleMoney(),
                        style = myTypography.body1,
                        color = Success)
                }
            }
        }
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 8.dp)
                .background(Primary200))
    }
}

@Composable
fun CRYContentBooksUI(content: @Composable () -> Unit){
    Card(
        Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)
    ) {
        content.invoke()
    }
}

