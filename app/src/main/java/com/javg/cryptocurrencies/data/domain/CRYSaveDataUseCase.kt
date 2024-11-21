package com.javg.cryptocurrencies.data.domain

import android.util.Log
import com.javg.cryptocurrencies.data.db.dao.CRYGeneralBooksDao
import com.javg.cryptocurrencies.data.mapper.toEntity
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import javax.inject.Inject

class CRYSaveDataUseCase @Inject constructor(
    private val generalBooksDao: CRYGeneralBooksDao
) {
    suspend operator fun invoke(generalBooks: List<CRYGeneralBook>){
        generalBooksDao.insertAll( generalBooks.map { it.toEntity() })
        Log.i("CRYSaveDataUseCase", "---- save data in db")
    }
}