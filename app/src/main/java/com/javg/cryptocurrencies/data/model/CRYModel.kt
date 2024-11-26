package com.javg.cryptocurrencies.data.model

data class CRYDetailBook(
    var book: String = "",
    var high: String = "",
    var last: String = "",
    var low: String = "",
    var askList: List<CRYAskOrBids> = emptyList(),
    var bidsList: List<CRYAskOrBids> = emptyList(),
)

data class CRYDefaultBook(
    var acronym: String = "",
    var name: String = "",
    var logoId: Int = 0
)

data class CRYGeneralBook(
    var fullName: String = "",
    var acronym: String = "",
    var logoId: Int = 0,
    var conversions: List<CRYBookV2> = emptyList()
)

data class CRYBookV2(
    var idBook: String,
    var nameConverter: String,
    var acronym: String,
    var maximumAmount: String,
    var rate: String,
    var imageId: Int
)
