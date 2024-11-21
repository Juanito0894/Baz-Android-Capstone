package com.javg.cryptocurrencies.data.domain

/**
 * @author Juan Vera Gomez
 * Date modified 21/11/2024
 *
 * The implementation to collect general ledger
 * information stored in the database
 *
 * @param generalBooksDao Interface in charge of providing consultation
 * of all the books saved in the database
 *
 * @since 1.0
 */
import com.javg.cryptocurrencies.data.db.dao.CRYGeneralBooksDao
import com.javg.cryptocurrencies.data.mapper.toEntity
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import javax.inject.Inject

class CRYSaveDataUseCase @Inject constructor(
    private val generalBooksDao: CRYGeneralBooksDao
) {
    suspend operator fun invoke(generalBooks: List<CRYGeneralBook>) = generalBooksDao.insertAll( generalBooks.map { it.toEntity() })
}