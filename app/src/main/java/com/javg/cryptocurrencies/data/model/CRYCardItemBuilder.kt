package com.javg.cryptocurrencies.data.model

class CRYCardItemBuilder: CRYCardItemBI {
    private var title = ""
    private var subtitle = ""
    private var iconId = 0
    private var haveButton = false
    private var textMoney = ""
    private var textSubtitleMoney = ""
    private var callBack: (() -> Unit )? = null

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

    override fun getTitle() = title

    override fun getSubtitle() = subtitle

    override fun getIconId() = iconId

    override fun getButtonAction() = haveButton

    override fun getTextMoney() = textMoney

    override fun getTextSubtitleMoney() = textSubtitleMoney

    override fun getOnClick() = callBack!!

}