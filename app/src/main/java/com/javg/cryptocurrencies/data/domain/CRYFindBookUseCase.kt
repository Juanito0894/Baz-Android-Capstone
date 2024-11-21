package com.javg.cryptocurrencies.data.domain

import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.repository.CRYBookRepository
import javax.inject.Inject

class CRYFindBookUseCase @Inject constructor(
    private val bookRepository: CRYBookRepository
) {
    suspend operator fun invoke(acronym: String): CRYGeneralBook? = bookRepository.findBookById(acronym)
}