package com.javg.cryptocurrencies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.javg.cryptocurrencies.utils.CRYUtils
import com.javg.cryptocurrencies.view.navigation.CRYNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.encodeUtf8
import java.nio.charset.StandardCharsets
import java.text.Normalizer

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoading by remember {
                mutableStateOf(false)
            }
            CRYNavigation()
            /*val refreshState = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = {
                    isLoading = true
                    Handler().postDelayed({
                        isLoading = false
                        Toast.makeText(this@MainActivityCompose,"onRefresh", Toast.LENGTH_SHORT).show()
                    },4000)
                })
            val elements = (1..400).map { "Item $it" }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                LazyColumn(Modifier.pullRefresh(refreshState)) {
                    items(elements){
                        Text(text = it)
                    }
                }
                PullRefreshIndicator(
                    refreshing = isLoading,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter))
            }*/
        }
    }
}