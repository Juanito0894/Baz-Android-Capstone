package com.javg.cryptocurrencies.view.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class CRYNavItem(
    internal val baseRoute: String,
    private val navArgs: List<CRYNavArgs> = emptyList()) {
    object DashboardNavItem: CRYNavItem("dashboard")
    object CollectionCoinsNavItem: CRYNavItem("collectionCoins")
    object DetailBook: CRYNavItem("detailBook", listOf(CRYNavArgs.BookName))

    val route = run {
        val argValue = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValue)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key){
            type = it.navType
        }
    }
}

enum class CRYNavArgs(val key: String, val navType: NavType<*>){
    BookName("bookName", NavType.StringType)
}