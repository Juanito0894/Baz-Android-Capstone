package com.javg.cryptocurrencies.data.domain

import com.javg.cryptocurrencies.data.db.dao.CRYTickerDao
import com.javg.cryptocurrencies.data.mapper.toEntity
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import javax.inject.Inject

class CRYSaveDetailBookUseCase @Inject constructor(
    private val tickerDao: CRYTickerDao
) {
    suspend operator fun invoke(detailBook: CRYDetailBook) = tickerDao.insert(detailBook.toEntity())
}