package com.javg.cryptocurrencies.data.domain

import android.util.Log
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.repository.CRYBookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author Juan Vera Gomez
 * Date modified 22/02/2023
 *
 * Contains the necessary function to get the information
 * from books to the repository layer
 *
 * @param repository It is the repository that is responsible for obtaining the books
 *
 * @since 2.1
 */
class CRYGetBookUseCase @Inject constructor(
    private val repository: CRYBookRepository,
    private val cryTransformBooksUseCase: TransformBooksUseCase,
    private val crySaveDataUseCase: CRYSaveDataUseCase
) {
    suspend operator fun invoke(): Flow<List<CRYGeneralBook>> = flow{
        repository.queryGeneralBooks().collect{
            Log.i("CRYGetBookUseCase", "-------------- invoke list from db is $it")
            if (it.isNotEmpty()){
                Log.i("CRYGetBookUseCase", "-------------- invoke if emit list to front")
                emit(it)
            } else {
                val generalBooks = cryTransformBooksUseCase(repository.getAvailableBooksFromApi())
                crySaveDataUseCase(generalBooks)
                Log.i("CRYGetBookUseCase", "-------------- invoke list from transformation from api useCase and save in db -> $generalBooks")
            }
        }
    }

    fun buildListExample(): List<String>{
        val news = listOf("A","Otra","Mia","IA")
        return buildList {
            news.forEach {
                add(it)
            }
        }
    }

}
