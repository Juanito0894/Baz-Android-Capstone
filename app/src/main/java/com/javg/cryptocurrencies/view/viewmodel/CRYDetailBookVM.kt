package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.domain.CRYFindBookUseCase
import com.javg.cryptocurrencies.data.domain.CRYGetListBookWithTickerUseCase
import com.javg.cryptocurrencies.data.model.CRYAskOrBids
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.utils.CRYUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 *
 * Contains the functionality to consult the specific information of each book
 *
 * @param tickerUseCase It is the case of use who manages
 * the obtaining of information in the data layer
 *
 * @since 2.0
 */
@HiltViewModel
class CRYDetailBookVM @Inject constructor(
    application: Application,
    private val tickerUseCase: CRYGetListBookWithTickerUseCase,
    private val findBookUseCase: CRYFindBookUseCase
) : AndroidViewModel(application) {

    private val _responseDetailBook = MutableStateFlow<CRYDataState<CRYDetailBook>>(CRYDataState.Idle)
    val responseDetailBook = _responseDetailBook.asStateFlow()

    private val _generalBook = MutableStateFlow<CRYGeneralBook>(CRYGeneralBook())
    val generalBook = _generalBook.asStateFlow()

    private var _tickerBook = MutableLiveData<CRYDetailBook>()
    private var _listAskOrBids = MutableLiveData<List<CRYAskOrBids>>()

    val tickerBook: LiveData<CRYDetailBook>
        get() = _tickerBook

    val listAskOrBids: LiveData<List<CRYAskOrBids>>
        get() = _listAskOrBids

    /**
     * Consult the price information and ask list and bids of a specific book
     *
     * @param book is the name of the book to consult its specific information
     */
    fun getTicker(book: String) {
        _responseDetailBook.value = CRYDataState.Loading
        viewModelScope.launch {
            _responseDetailBook.value = tickerUseCase.invoke(book)
        }
    }

    fun getInfoBook(acronym: String){
        viewModelScope.launch {
            val book = findBookUseCase(acronym)
            if (book != null){
                _generalBook.update {
                    it.copy(fullName = book.fullName)
                }
            }
        }
    }
}
