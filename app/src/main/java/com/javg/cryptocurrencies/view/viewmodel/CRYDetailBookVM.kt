package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.javg.cryptocurrencies.data.domain.CRYFindBookUseCase
import com.javg.cryptocurrencies.data.domain.CRYFindNameBookUseCase
import com.javg.cryptocurrencies.data.domain.CRYGetListBookWithTickerUseCase
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeList
import com.javg.cryptocurrencies.data.model.CRYAskOrBids
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
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
    private val getNameBookUseCase: CRYFindNameBookUseCase
) : AndroidViewModel(application) {

    private val _responseDetailBook = MutableStateFlow<CRYDataState<CRYDetailBook>>(CRYDataState.Idle)
    val responseDetailBook = _responseDetailBook.asStateFlow()

    var fullName = MutableStateFlow<String>("")
        private set

    private var _listAskOrBids = MutableStateFlow<List<CRYAskOrBids>>(emptyList())
    val listAskOrBids = _listAskOrBids.asStateFlow()

    private var _tickerBook = MutableLiveData<CRYDetailBook>()
    val tickerBook: LiveData<CRYDetailBook>
        get() = _tickerBook

    /**
     * Consult the price information and ask list and bids of a specific book
     *
     * @param book is the name of the book to consult its specific information
     */
    fun getTicker(book: String) {
        _responseDetailBook.value = CRYDataState.Loading
        viewModelScope.launch {
            val tickerModel = tickerUseCase.invoke(book)
            if (tickerModel is CRYDataState.Success) {
                _listAskOrBids.value = tickerModel.data.askList
            }
            _responseDetailBook.value = tickerModel
        }
    }

    fun getInfoBook(acronym: String){
        fullName.value = getNameBookUseCase(acronym)
    }

    fun updateInformationBooks(typeList: CRYEnumsTypeList) {
        when (typeList) {
            CRYEnumsTypeList.ASK -> {
                _listAskOrBids.value = (_responseDetailBook.value as CRYDataState.Success).data.askList
            }
            CRYEnumsTypeList.BIDS -> {
                _listAskOrBids.value = (_responseDetailBook.value as CRYDataState.Success).data.bidsList
            }
        }
    }
}
