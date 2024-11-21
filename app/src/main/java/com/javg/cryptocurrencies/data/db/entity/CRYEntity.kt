package com.javg.cryptocurrencies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.javg.cryptocurrencies.data.db.remote.CRYAppDatabase

@Entity(tableName = CRYAppDatabase.DETAIL_BOOK_TABLE)
data class CRYDetailBookEntity(
    @PrimaryKey
    @ColumnInfo("book")
    val book: String,
    @ColumnInfo("high") val high: String,
    @ColumnInfo("last") val last: String,
    @ColumnInfo("low") val low: String,
    @ColumnInfo("askList") val askList: String,
    @ColumnInfo("bidsList") val bidsList: String,
)

@Entity(tableName = CRYAppDatabase.GENERAL_BOOKS_TABLE)
data class CRYGeneralBooksEntity(
    @PrimaryKey
    @ColumnInfo("acronym")
    val acronym: String,
    @ColumnInfo("fullName")
    val fullName: String,
    @ColumnInfo("imageId")
    val imageId: Int,
    @ColumnInfo("collectionBooks")
    val collectionBooks: String
)
