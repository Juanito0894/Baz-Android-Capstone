package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.javg.cryptocurrencies.utils.CRYUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CRYAppViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiStateNetwork = MutableStateFlow(true)

    val uiStateNetwork: StateFlow<Boolean> = _uiStateNetwork

    init {
        Log.i("CRYAppViewModel","Initialization app viewModel")
        CRYUtils.isInternetAvailable(application.applicationContext) {
            _uiStateNetwork.value = it
        }
    }
}
