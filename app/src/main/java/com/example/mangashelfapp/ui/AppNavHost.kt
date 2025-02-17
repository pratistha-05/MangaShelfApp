package com.example.mangashelfapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mangashelfapp.ui.screen.MangaDetailScreen
import com.example.mangashelfapp.ui.screen.MangaListScreen

@Composable
fun MangaAppNavHost(innerPadding: PaddingValues) {
  val navController = rememberNavController()

  NavHost(
    navController = navController,
    startDestination = "manga_list"
  ) {
    composable("manga_list") {
          MangaListScreen(innerPadding,navController)
    }

    composable(
      route = "manga_detail/{mangaId}",
      arguments = listOf(navArgument("mangaId") { type = NavType.StringType })
    ) { backStackEntry ->
      val mangaId = backStackEntry.arguments?.getString("mangaId") ?: ""
      MangaDetailScreen( mangaId)
    }
  }
}
