package com.javg.cryptocurrencies.data.domain

import com.javg.cryptocurrencies.utils.CRYMockData
import javax.inject.Inject

class CRYFindNameBookUseCase @Inject constructor(){
    operator fun invoke(acronym: String): String{
        val mockBook = CRYMockData.bookDefaults.find { it.acronym == acronym }
        return mockBook?.name ?: acronym.uppercase()
    }
}