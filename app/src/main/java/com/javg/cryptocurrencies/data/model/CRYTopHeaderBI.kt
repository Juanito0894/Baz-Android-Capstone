package com.javg.cryptocurrencies.data.model

import com.javg.cryptocurrencies.data.enums.CRYEnumsTopBar

interface CRYTopHeaderBI {
    fun withTitle(title: String): CRYTopHeaderBuilder
    fun withTypeHeader(type: CRYEnumsTopBar): CRYTopHeaderBuilder
    fun withOnClick(action: (() -> Unit)?): CRYTopHeaderBuilder
    fun getTitle(): String
    fun getType(): CRYEnumsTopBar
    fun getOnClick(): (() -> Unit)?
}