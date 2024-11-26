package com.javg.cryptocurrencies.data.domain

import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.model.CRYOrderBook
import com.javg.cryptocurrencies.data.model.CRYTicker
import javax.inject.Inject

class CRYBuildDetailBookUseCase @Inject constructor() {
    operator fun invoke(ticker: CRYTicker, orderBook: CRYOrderBook): CRYDetailBook = CRYDetailBook(
        book = ticker.book,
        high = ticker.high,
        last = ticker.last,
        low = ticker.low,
        askList = orderBook.asksList,
        bidsList = orderBook.bidsList)
}