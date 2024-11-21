package com.javg.cryptocurrencies.data.domain

import android.util.Log
import com.javg.cryptocurrencies.R
import com.javg.cryptocurrencies.data.model.CRYBookResponse
import com.javg.cryptocurrencies.data.model.CRYBookV2
import com.javg.cryptocurrencies.data.model.CRYDefaultBook
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.utils.CRYMockData
import com.javg.cryptocurrencies.utils.getFirstCoinsText
import com.javg.cryptocurrencies.utils.getSecondCoinsText
import com.javg.cryptocurrencies.utils.separateStringCoins
import javax.inject.Inject

class TransformBooksUseCase @Inject constructor() {

    operator fun invoke(booksResponse: List<CRYBookResponse>): List<CRYGeneralBook>{
        val unrepeatedBooks = booksResponse.distinctBy { it.book.getFirstCoinsText() }
        Log.i("queryBooksDummy", "-------------- Tamaño de lista de libros inicial -> ${booksResponse.size}")
        return buildList {
            unrepeatedBooks.forEach { unrepeatedBook ->

                Log.i("queryBooksDummy", "-------------- Nombre del libro -> ${unrepeatedBook.book.separateStringCoins()}")

                val bookGroup = booksResponse.filter { it.book.getFirstCoinsText() == unrepeatedBook.book.getFirstCoinsText() }
                val mockBook = CRYMockData.bookDefaults.find { it.acronym == unrepeatedBook.book.getFirstCoinsText() } ?:
                run { CRYDefaultBook(unrepeatedBook.book,unrepeatedBook.book.toUpperCase(), R.drawable.ic_default_book) }

                val newGroupBooks = mutableListOf<CRYBookV2>()

                Log.i("queryBooksDummy", "-------------- Tamaño del grupo de libros -> ${bookGroup.size}")

                bookGroup.forEach { book ->
                    Log.i("bookGroup", "-------------- name -> ${book.book}")

                    val mockBookUnrepeated = CRYMockData.bookDefaults.find { it.acronym == book.book.getSecondCoinsText() } ?:
                    run { CRYDefaultBook(book.book.getSecondCoinsText(),book.book.getSecondCoinsText().toUpperCase(),
                        R.drawable.ic_default_book) }

                    newGroupBooks.add(
                        CRYBookV2(
                            idBook = book.book,
                            nameConverter = mockBookUnrepeated.name,
                            acronym = mockBookUnrepeated.acronym,
                            maximumAmount = book.maximumAmount,
                            rate = "${book.tickSize}%",
                            imageId = mockBookUnrepeated.logoId)
                    )
                }
                val newGeneralBook = CRYGeneralBook(
                    fullName = mockBook.name,
                    acronym = mockBook.acronym,
                    logoId = mockBook.logoId,
                    conversions = newGroupBooks)

                add(newGeneralBook)
            }

        }
    }
}