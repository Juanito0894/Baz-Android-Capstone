package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javg.cryptocurrencies.data.domain.CRYFindBookUseCase
import com.javg.cryptocurrencies.data.model.CRYBookV2
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CRYCollectionsBooksVM @Inject constructor(
    application: Application,
    private val cryFindBookUseCase: CRYFindBookUseCase
): AndroidViewModel(application) {
    private val _generalBook = MutableLiveData<CRYGeneralBook>(null)
    val generalBook: LiveData<CRYGeneralBook> get() = _generalBook

    fun findGeneralBook(acronym: String){
        viewModelScope.launch {
           cryFindBookUseCase(acronym)?.let {
               _generalBook.value = it
           }
        }
    }
}