package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.javg.cryptocurrencies.data.domain.CRYGetBookUseCase
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeFlow
import com.javg.cryptocurrencies.data.model.CRYBookV2
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 21/11/2024
 *
 * Contains the necessary functions to obtain the information
 * from the cryptocurrency books
 *
 * @since 2.0
 */
@HiltViewModel
class CRYHomeVM @Inject constructor(
    application: Application,
    private val cryGetBookUseCase: CRYGetBookUseCase,
) : AndroidViewModel(application) {
    private val _stateGeneralBooks = MutableStateFlow<CRYDataState<List<CRYGeneralBook>>>(CRYDataState.Idle)
    val stateGeneralBooks = _stateGeneralBooks.asStateFlow()

    private val _originalList = MutableStateFlow<List<CRYGeneralBook>>(emptyList())

    init {
        queryBookFlow()
    }

    /**
     * It is responsible for requesting the list of books from the data
     * layer which can be called with user interaction.
     *
     */
    fun getBooks() {
        /*val result = bookUseCase.getAvailableBooksRx()
        _result.postValue(result)*/
    }

    /**
     * It is in charge of observing the changes of the list of books
     * in the database and in case it has changed, it refreshes the view
     * with the new information stored in it.
     */
    private fun queryBookFlow() {
        viewModelScope.launch {
            _stateGeneralBooks.value = CRYDataState.Loading
            cryGetBookUseCase().collect{
                when(it) {
                    is CRYDataState.Success -> _originalList.value = it.data
                    else -> {}
                }
                _stateGeneralBooks.value = it
            }
        }
    }

    fun searchBook(value: String){
        val generalBooksAux = _originalList.value.filter { it.fullName.lowercase().contains(value) }
        _stateGeneralBooks.value = CRYDataState.Success(generalBooksAux)
    }

    fun getTypeView(collections: List<CRYBookV2>) = if (collections.size > 1) CRYEnumsTypeFlow.COLLECTIONS else CRYEnumsTypeFlow.SINGLE

    fun getIdBook(book: CRYGeneralBook): String{
        return if (book.conversions.size > 1) book.acronym else "${book.acronym}|${book.conversions.first().idBook}"
    }

}
