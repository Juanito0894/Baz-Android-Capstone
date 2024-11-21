package com.javg.cryptocurrencies.data.model

interface CRYCardItemBI {
    fun withTitle(title: String): CRYCardItemBuilder
    fun withSubtitle(subtitle: String): CRYCardItemBuilder
    fun withIconId(iconId: Int): CRYCardItemBuilder
    fun withButtonAction(haveButton: Boolean): CRYCardItemBuilder
    fun withTextMoney(textMoney: String): CRYCardItemBuilder
    fun withTextSubtitleMoney(textMoney: String): CRYCardItemBuilder
    fun withOnClick(action: () -> Unit): CRYCardItemBuilder
    fun withHaveCollections(haveCollections: Boolean): CRYCardItemBuilder

    fun getTitle(): String
    fun getSubtitle(): String
    fun getIconId(): Int
    fun getButtonAction(): Boolean
    fun getTextMoney(): String
    fun getTextSubtitleMoney(): String
    fun getOnClick(): () -> Unit
    fun getHaveCollections(): Boolean
}