package com.javg.cryptocurrencies.data.repository

import android.content.Context
import com.javg.cryptocurrencies.data.db.dao.CRYTickerDao
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYTicker
import com.javg.cryptocurrencies.data.network.CRYApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 16/02/2023
 *
 * Allows you to retrieve business information from the specified book.
 *
 * @param cryApi is an interface that contains the remote query endpoints
 *
 * @since 3.0
 */
class CRYTickerRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val cryApi: CRYApi,
    private val tickerDao: CRYTickerDao,
) : CRYGenericRepository() {

    suspend fun getTickerFromApi(book: String): CRYDataState<CRYTicker> {
        var responseAux: CRYDataState<CRYTicker>? = null
        delay(3000)
        getResponseV2(
            context = context,
            callFunction = { cryApi.getTicker(book)},
            onSuccess = { response ->
                if (response.success) {
                    response.payload?.let {
                        responseAux = CRYDataState.Success(it)
                    } ?: run {
                        responseAux = CRYDataState.Error("Algun fallo hubo!")
                    }
                } else {
                    responseAux = CRYDataState.Error("Algun fallo hubo!")
                }
            },
            onError = {
                responseAux = CRYDataState.Error(it)
            })
        return responseAux!!
    }
}
