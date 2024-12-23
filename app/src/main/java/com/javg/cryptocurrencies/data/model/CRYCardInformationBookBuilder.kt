package com.javg.cryptocurrencies.data.model

class CRYCardInformationBookBuilder: CRYCardInformationBookBI {
    private var price = ""
    private var amount = ""

    override fun withPrice(price: String): CRYCardInformationBookBuilder {
        this.price = price
        return this
    }

    override fun withAmount(amount: String): CRYCardInformationBookBuilder {
        this.amount = amount
        return this
    }

    override fun getPrice() = price

    override fun getAmount() = amount
}