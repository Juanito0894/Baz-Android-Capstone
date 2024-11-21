package com.javg.cryptocurrencies.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.javg.cryptocurrencies.data.domain.CRYGetBookUseCase
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeFlow
import com.javg.cryptocurrencies.data.model.CRYBookV2
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private var _bookInitials = MutableStateFlow("")
    val bookInitials: StateFlow<String> get() = _bookInitials

    private val _bookCollections = MutableStateFlow(emptyList<CRYBookV2>())
    val bookCollections: StateFlow<List<CRYBookV2>> get() = _bookCollections

    private val _stateGeneralBooks = MutableStateFlow(emptyList<CRYGeneralBook>())
    val stateGeneralBooks: StateFlow<List<CRYGeneralBook>> get() = _stateGeneralBooks

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
            cryGetBookUseCase().collect{
                _stateGeneralBooks.value = it
            }
        }
    }

    fun getTypeView(collections: List<CRYBookV2>) = if (collections.size > 1) CRYEnumsTypeFlow.COLLECTIONS else CRYEnumsTypeFlow.SINGLE

    fun setBookInitials(bookInitials: String){
        _bookInitials.value = bookInitials
    }

    fun queryCollections(){
        val bookSelected = stateGeneralBooks.value.find { it.acronym == bookInitials.value }
        _bookCollections.value = bookSelected?.conversions ?: emptyList()
    }
}
