package com.javg.cryptocurrencies.data.domain

import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.repository.CRYBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 21/11/2024
 *
 * Contains the necessary function to get the information
 * from books to the repository layer
 *
 * @param repository It is the repository that is responsible for obtaining the books
 *
 * @since 3.0
 */
class CRYGetBookUseCase @Inject constructor(
    private val repository: CRYBookRepository,
    private val cryTransformBooksUseCase: TransformBooksUseCase,
    private val crySaveDataUseCase: CRYSaveDataUseCase
) {
    suspend operator fun invoke(): Flow<List<CRYGeneralBook>> = flow{
        repository.queryGeneralBooks().collect{
            if (it.isNotEmpty()){
                emit(it)
            } else {
                val generalBooks = cryTransformBooksUseCase(repository.getAvailableBooksFromApi())
                crySaveDataUseCase(generalBooks)
            }
        }
    }
}
