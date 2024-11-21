package com.javg.cryptocurrencies.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.javg.cryptocurrencies.data.enums.CRYEnumsTypeFlow
import com.javg.cryptocurrencies.view.dashboard.CRYDashboardBooksScreen
import com.javg.cryptocurrencies.view.collections_book.CRYCollectionsBookScreen
import com.javg.cryptocurrencies.view.detail.CRYDetailBookScreen
import com.javg.cryptocurrencies.view.viewmodel.CRYHomeVM

@Composable
fun CRYNavigation(){
    val navController = rememberNavController()
    val homeVM: CRYHomeVM = hiltViewModel()

    NavHost(navController = navController, startDestination = CRYScreen.Dashboard.baseRoute) {
        composable(CRYScreen.Dashboard.baseRoute) {
            CRYDashboardBooksScreen(homeVM){typeFlow, acronym ->
                when(typeFlow) {
                    CRYEnumsTypeFlow.COLLECTIONS -> navController.navigate(CRYScreen.CollectionBooks.baseRoute+"/${acronym}")
                    CRYEnumsTypeFlow.SINGLE -> navController.navigate(CRYScreen.DetailBook.baseRoute)
                }
            }
        }
        composable(
            CRYScreen.CollectionBooks.baseRoute+"/{acronym}",
            arguments = listOf(navArgument("acronym"){type = NavType.StringType})){ backStackEntry ->
            val acronym = requireNotNull(backStackEntry.arguments?.getString("acronym"))
            CRYCollectionsBookScreen(acronym){
                navController.popBackStack()
            }
        }
        composable(CRYScreen.DetailBook.baseRoute){
            CRYDetailBookScreen()
        }
    }
}

sealed class CRYScreen(internal val baseRoute: String){
    object Dashboard: CRYScreen("Dashboard")
    object CollectionBooks: CRYScreen("CollectionsBooks")
    object DetailBook: CRYScreen("DetailBook")
}

