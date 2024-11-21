package com.javg.cryptocurrencies.data.db.remote

import androidx.room.Database
import androidx.room.RoomDatabase
import com.javg.cryptocurrencies.data.db.dao.CRYGeneralBooksDao
import com.javg.cryptocurrencies.data.db.dao.CRYTickerDao
import com.javg.cryptocurrencies.data.db.entity.CRYDetailBookEntity
import com.javg.cryptocurrencies.data.db.entity.CRYGeneralBooksEntity

@Database(entities = [CRYDetailBookEntity::class, CRYGeneralBooksEntity::class], version = 1)
abstract class CRYAppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "database_crypto_book"
        const val DETAIL_BOOK_TABLE = "detail_book_table"
        const val GENERAL_BOOKS_TABLE = "general_books_table"
    }

    abstract fun tickerDao(): CRYTickerDao

    abstract fun generalBooks(): CRYGeneralBooksDao

}
