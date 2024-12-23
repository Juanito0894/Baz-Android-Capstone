package com.javg.cryptocurrencies

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.javg.cryptocurrencies.utils.CRYUtils
import com.javg.cryptocurrencies.view.navigation.CRYNavigation
import com.javg.cryptocurrencies.view.viewmodel.CRYAppViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
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
            val appVM: CRYAppViewModel = hiltViewModel()
            val stateNetwork =appVM.uiStateNetwork.collectAsState()

            var isLoading by remember {
                mutableStateOf(false)
            }
            Log.i("MainActivityCompose","stateNetwork => $stateNetwork")
            Box{
                CRYNavigation()
                if (!stateNetwork.value) {
                    Dialog(onDismissRequest = {},
                        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)) {
                        val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider

                        dialogWindowProvider?.window?.let { window ->
                            window.setDimAmount(0f)
                        }
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(bottom = 8.dp),
                            contentAlignment = Alignment.BottomCenter){
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(Color.Red)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = "Sin conexión a internet",
                                    color = Color.White,
                                    fontSize = 16.sp)
                            }
                        }
                    }
                }
            }

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