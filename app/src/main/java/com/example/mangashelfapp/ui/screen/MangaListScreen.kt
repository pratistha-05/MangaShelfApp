package com.example.mangashelfapp.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangashelfapp.ui.UiState
import com.example.mangashelfapp.ui.components.MangaItem
import com.example.mangashelfapp.ui.viewmodel.MangaViewModel

@Composable
fun MangaListScreen(viewModel: MangaViewModel = hiltViewModel()) {
  val uiState by viewModel.uiState.collectAsState()

  when (uiState) {
    is UiState.Loading -> {
      CircularProgressIndicator()
    }

    is UiState.Success -> {
      val mangas = (uiState as UiState.Success).mangas
      LazyColumn {
        items(mangas) { manga ->
          MangaItem(manga)
        }
      }
    }

    is UiState.Error -> {
      val errorMessage = (uiState as UiState.Error).message
      Text("Error: $errorMessage", color = Color.Red)
    }
  }
}
