package com.example.elibreader

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elibreader.models.BookViewModel
import com.example.elibreader.screens.BookDetailScreen
import com.example.elibreader.screens.FavoritesScreen
import com.example.elibreader.screens.RegisterScreen
import com.example.elibreader.screens.SearchScreen
import com.example.elibreader.screens.SettingsScreen
import com.example.elibreader.ui.auth.LoginScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()




    NavHost(navController = navController, startDestination = Routes.loginScreen, builder = {
        composable(Routes.loginScreen) {
            LoginScreen(navController, viewModel)
        }

        composable(Routes.registerScreen) {
            RegisterScreen(navController, viewModel)
        }

        composable(Routes.searchScreen) {
            SearchScreen(navController, viewModel)
        }

        composable(Routes.settingsScreen) {
            SettingsScreen(navController)
        }

        composable(Routes.bookDetailScreen) {
                BookDetailScreen(navController, viewModel, )

        }

        composable(Routes.favoritesScreen) {
            FavoritesScreen(navController, viewModel)

        }
    })
}
