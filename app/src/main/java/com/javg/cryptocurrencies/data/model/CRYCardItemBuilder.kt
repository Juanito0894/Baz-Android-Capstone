package com.javg.cryptocurrencies.data.model

import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeCard

class CRYCardItemBuilder: CRYCardItemBI {
    private var title = ""
    private var subtitle = ""
    private var iconId = 0
    private var haveButton = false
    private var textMoney = ""
    private var textSubtitleMoney = ""
    private var callBack: (() -> Unit )? = null
    private var haveCollections = false
    private var onClickCard: (() -> Unit)? = null
    private var typeCard = CRYEnumsTypeCard.DASHBOARD

    override fun withTitle(title: String): CRYCardItemBuilder {
        this.title = title
        return this
    }

    override fun withSubtitle(subtitle: String): CRYCardItemBuilder {
        this.subtitle = subtitle
        return this
    }

    override fun withIconId(iconId: Int): CRYCardItemBuilder {
        this.iconId = iconId
        return this
    }

    override fun withButtonAction(haveButton: Boolean): CRYCardItemBuilder {
        this.haveButton = haveButton
        return this
    }

    override fun withTextMoney(textMoney: String): CRYCardItemBuilder {
        this.textMoney = textMoney
        return this
    }

    override fun withTextSubtitleMoney(textMoney: String): CRYCardItemBuilder {
        this.textSubtitleMoney = textMoney
        return this
    }

    override fun withOnClick(action: () -> Unit): CRYCardItemBuilder {
        this.callBack = action
        return this
    }

    override fun withHaveCollections(haveCollections: Boolean): CRYCardItemBuilder {
        this.haveCollections = haveCollections
        return this
    }

    override fun withOnClickCard(onClickCard: () -> Unit): CRYCardItemBuilder {
        this.onClickCard = onClickCard
        return this
    }

    override fun withTypeCard(typeCard: CRYEnumsTypeCard): CRYCardItemBuilder {
        this.typeCard = typeCard
        return this
    }

    override fun getTitle() = title

    override fun getSubtitle() = subtitle

    override fun getIconId() = iconId

    override fun getButtonAction() = haveButton

    override fun getTextMoney() = textMoney

    override fun getTextSubtitleMoney() = textSubtitleMoney

    override fun getOnClick() = callBack!!

    override fun getHaveCollections() = haveCollections

    override fun getOnClickCard() = onClickCard!!

    override fun getTypeCard() = typeCard
}