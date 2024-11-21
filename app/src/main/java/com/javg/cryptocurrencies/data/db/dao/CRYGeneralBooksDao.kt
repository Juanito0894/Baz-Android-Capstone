package com.javg.cryptocurrencies.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.javg.cryptocurrencies.data.db.entity.CRYGeneralBooksEntity
import com.javg.cryptocurrencies.data.db.remote.CRYAppDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface CRYGeneralBooksDao {
    @Query("SELECT * FROM ${CRYAppDatabase.GENERAL_BOOKS_TABLE}")
    fun getAllGeneralBooks(): Flow<List<CRYGeneralBooksEntity>>

    @Query("SELECT * FROM ${CRYAppDatabase.GENERAL_BOOKS_TABLE} WHERE acronym = :acronym")
    suspend fun findById(acronym: String): CRYGeneralBooksEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAll(generalBooks: List<CRYGeneralBooksEntity>)
}