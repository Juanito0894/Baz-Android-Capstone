package com.javg.cryptocurrencies.data.mapper

import com.javg.cryptocurrencies.data.db.entity.CRYDetailBookEntity
import com.javg.cryptocurrencies.data.db.entity.CRYGeneralBooksEntity
import com.javg.cryptocurrencies.data.model.CRYAskOrBids
import com.javg.cryptocurrencies.data.model.CRYBookV2
import com.javg.cryptocurrencies.data.model.CRYDetailBook
import com.javg.cryptocurrencies.data.model.CRYGeneralBook
import com.javg.cryptocurrencies.data.model.CRYTicker
import com.javg.cryptocurrencies.utils.CRYUtils

/**
 * A function is extended to be able to treat the model
 * of the response to an entity model
 */
fun CRYDetailBook.toEntity(): CRYDetailBookEntity {
    return CRYDetailBookEntity(
        book = this.book,
        high = this.high,
        last = this.last,
        low = this.low,
        askList = CRYUtils.listToJson(this.askList),
        bidsList = CRYUtils.listToJson(this.bidsList),
    )
}

/**
 * A function is extended from the entity model to
 * handle it and pass it to a general model
 */
fun CRYDetailBookEntity.toDomain(): CRYDetailBook {
    return CRYDetailBook(
        high = this.high,
        last = this.last,
        low = this.low,
        askList = CRYUtils.jsonToList<List<CRYAskOrBids>>(this.askList),
        bidsList = CRYUtils.jsonToList<List<CRYAskOrBids>>(this.bidsList)
    )
}

fun CRYGeneralBook.toEntity(): CRYGeneralBooksEntity {
    return CRYGeneralBooksEntity(
        acronym = this.acronym,
        fullName = this.fullName,
        imageId = this.logoId,
        collectionBooks = CRYUtils.listToJson(this.conversions))
}

fun CRYGeneralBooksEntity.toDomain(): CRYGeneralBook{
    return CRYGeneralBook(
        fullName = this.fullName,
        acronym = this.acronym,
        logoId = this.imageId,
        conversions = CRYUtils.jsonToList<List<CRYBookV2>>(this.collectionBooks))
}
