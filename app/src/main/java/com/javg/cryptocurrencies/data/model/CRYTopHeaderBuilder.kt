package com.javg.cryptocurrencies.data.model

import com.javg.cryptocurrencies.data.enum.CRYEnumsTopBar

class CRYTopHeaderBuilder: CRYTopHeaderBI {
    private var title = "Title"
    private var type = CRYEnumsTopBar.TEXT_ONLY
    private var callBack: (() -> Unit)? = null

    override fun withTitle(title: String): CRYTopHeaderBuilder {
        this.title = title
        return this
    }

    override fun withTypeHeader(type: CRYEnumsTopBar): CRYTopHeaderBuilder {
        this.type = type
        return this
    }

    override fun withOnClick(action: (() -> Unit)?): CRYTopHeaderBuilder {
        this.callBack = action
        return this
    }

    override fun getTitle() = title

    override fun getType() = type

    override fun getOnClick(): (() -> Unit)? = callBack
}