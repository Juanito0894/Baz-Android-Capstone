package com.javg.cryptocurrencies.data.repository

import android.content.Context
import com.javg.cryptocurrencies.data.db.dao.CRYGeneralBooksDao
import com.javg.cryptocurrencies.data.mapper.toDomain
import com.javg.cryptocurrencies.data.model.CRYBookResponse
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.network.CRYApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 21/11/2024
 *
 * It is in charge of the functionality to be able to obtain the information
 * from the database and in case of not having information,
 * obtain it remotely through a service
 *
 * @since 3.0
 */
class CRYBookRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val cryApi: CRYApi,
    private val generalBookDao: CRYGeneralBooksDao
) : CRYGenericRepository() {

    /**
     * Observe through a flow the list of books stored in the database every time it changes
     *
     * @return a generalBook list type flow
     */
    fun queryGeneralBooks(): Flow<List<CRYGeneralBook>> = generalBookDao.getAllGeneralBooks().map { books ->
        books.map { it.toDomain() }
    }

    suspend fun getAvailableBooksFromApi(): CRYDataState<List<CRYBookResponse>>{
        delay(5000)
        var responseAux: CRYDataState<List<CRYBookResponse>>? = null

        getResponseV2(
            context = context,
            callFunction = {cryApi.getAvailableBooks()},
            onSuccess = { response ->
                if (response.success) {
                    response.payload?.let {
                        responseAux = CRYDataState.Success(it)
                    } ?: run {
                        responseAux = CRYDataState.Error("Información por el momento no habilitada")
                    }
                } else {
                    responseAux = CRYDataState.Error("Información por el momento no habilitada")
                }
            }
        ){
            responseAux =CRYDataState.Error(it)
        }
        return responseAux!!
    }

    suspend fun findBookById(acronym: String): CRYGeneralBook? {
        return generalBookDao.findById(acronym)?.toDomain()
    }
}
