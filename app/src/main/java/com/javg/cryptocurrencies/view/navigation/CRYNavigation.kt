package com.javg.cryptocurrencies.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.javg.cryptocurrencies.view.book.CRYDashboardBooksScreen
import com.javg.cryptocurrencies.view.collections_book.CRYCollectionsBookScreen
import com.javg.cryptocurrencies.view.detail.CRYDetailBookScreen

@Composable
fun CRYNavigation(){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = CRYNavItem.DashboardNavItem.route) {
        composable(CRYNavItem.DashboardNavItem.baseRoute) {
            CRYDashboardBooksScreen{
                // navController.navigate(CRYNavItem.CollectionCoinsNavItem.baseRoute)
                navController.navigate(CRYNavItem.DetailBook.baseRoute)
            }
        }
        composable(CRYNavItem.CollectionCoinsNavItem.baseRoute){
            CRYCollectionsBookScreen{
                navController.popBackStack()
            }
        }
        composable(CRYNavItem.DetailBook.baseRoute){
            CRYDetailBookScreen()
        }
    }
}