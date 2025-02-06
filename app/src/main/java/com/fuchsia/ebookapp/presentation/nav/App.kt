package com.fuchsia.ebookapp.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fuchsia.ebookapp.presentation.screen.BookByCategoryScreen
import com.fuchsia.ebookapp.presentation.screen.PdfViewScreen
import com.fuchsia.ebookapp.presentation.screen.TabScreen


@Composable
fun App() {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Routes.Home) {

        composable<Routes.Home> {
            TabScreen(navController)
        }
        composable<Routes.BookByCategory> {}

        composable<Routes.PdfViewScreen> {
            val data = it.toRoute<Routes.PdfViewScreen>()
            PdfViewScreen(pdfUrl = data.pdfUrl)
        }
        composable<Routes.BookByCategoryScreen> {

            val data = it.toRoute<Routes.BookByCategoryScreen>()
            BookByCategoryScreen(navController = navController, category = data.category)
        }

    }

}