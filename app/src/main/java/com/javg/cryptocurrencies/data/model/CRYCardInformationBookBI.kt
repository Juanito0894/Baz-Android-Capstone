package com.javg.cryptocurrencies.data.model

interface CRYCardInformationBookBI {
    fun withPrice(price: String):CRYCardInformationBookBuilder
    fun withAmount(amount: String): CRYCardInformationBookBuilder
    fun getPrice(): String
    fun getAmount(): String
}