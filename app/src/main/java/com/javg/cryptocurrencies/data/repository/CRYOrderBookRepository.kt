package com.javg.cryptocurrencies.data.repository

import android.content.Context
import com.javg.cryptocurrencies.data.db.dao.CRYTickerDao
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYOrderBook
import com.javg.cryptocurrencies.data.network.CRYApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 13/02/2023
 *
 * allows you to retrieve a list of all open orders in
 * the specified book.
 *
 * @param cryApi is an interface that contains the remote query endpoints
 *
 * @since 3.0
 */
class CRYOrderBookRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryApi: CRYApi,
    private val tickerDao: CRYTickerDao,
): CRYGenericRepository() {
    /**
     * Returns a book order type model consulting the information remotely to an api
     *
     * @param book is the name of the book to consult its specific information
     */
    suspend fun getOrderBookFromApi(book: String): CRYDataState<CRYOrderBook>{
        var responseAux: CRYDataState<CRYOrderBook>? = null
        getResponseV2(
            context = context,
            callFunction = {cryApi.getOrderBook(book)},
            onSuccess = { response ->
                if (response.success) {
                    response.payload?.let {
                        responseAux = CRYDataState.Success(it)
                    }?: run {
                        responseAux = CRYDataState.Error("Algun fallo!!")
                    }
                } else {
                    responseAux = CRYDataState.Error("Algun fallo!!")
                }
            },
            onError = {
                responseAux = CRYDataState.Error(it)
            })
        return responseAux!!
    }
}
