package com.javg.cryptocurrencies.data.domain

import android.util.Log
import com.javg.cryptocurrencies.data.model.CRYDataState
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.repository.CRYOrderBookRepository
import com.javg.cryptocurrencies.data.repository.CRYTickerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 13/02/2023
 *
 * Contains the functionality to query the information and make
 * a deal before returning the information to the view model
 *
 * @param tickerRepository is the repository that returns only ticker information
 * @param orderBookRepository is the repository that returns only order book information
 *
 * @since 2.0
 */
class CRYGetListBookWithTickerUseCase @Inject constructor(
    private val tickerRepository: CRYTickerRepository,
    private val orderBookRepository: CRYOrderBookRepository,
    private val dispatcher: CoroutineDispatcher,
    private val buildDetailBookUseCase: CRYBuildDetailBookUseCase,
    private val saveDetailBookUseCase: CRYSaveDetailBookUseCase
) {

    /**
     * Returns a detail type model of a specific book,
     * obtaining the information from two different repositories
     *
     * @param book is the name of the book to consult its information
     *
     * @return CRYDetailBook is the detail model of the consulted book
     */
    suspend operator fun invoke(book: String): CRYDataState<CRYDetailBook> = withContext(dispatcher) {
        val detailBook: CRYDataState<CRYDetailBook>?
        val detailDB = orderBookRepository.getByIdOrderBook(book)

        if (detailDB != null) {
            detailBook = CRYDataState.Success(detailDB)
        } else {
            val ticker = async { tickerRepository.getTickerFromApi(book) }.await()
            val orderBook = async { orderBookRepository.getOrderBookFromApi(book) }.await()

            if (ticker is CRYDataState.Success && orderBook is CRYDataState.Success){
                val buildDetailBook = buildDetailBookUseCase(ticker.data,orderBook.data)
                saveDetailBookUseCase(buildDetailBook)
                detailBook = CRYDataState.Success(buildDetailBook)
            } else {
                detailBook = CRYDataState.Error((ticker as CRYDataState.Error).message)
            }
        }
        detailBook
    }
}
