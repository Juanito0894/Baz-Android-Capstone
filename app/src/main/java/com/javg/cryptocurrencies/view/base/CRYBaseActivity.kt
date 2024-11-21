package com.javg.cryptocurrencies.view.base

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.javg.cryptocurrencies.view.viewmodel.CRYAppViewModel
import kotlinx.coroutines.launch

open class CRYBaseActivity : AppCompatActivity() {
    private val appVM by viewModels<CRYAppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testStateFlow()
    }

    private fun testStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                appVM.uiStateNetwork.collect {
                    if (it) {
                        showSuccessNetwork()
                    } else {
                        showErrorNetwork()
                    }
                }
            }
        }
    }

    private fun showSuccessNetwork() {
    }

    private fun showErrorNetwork() {

    }
}
