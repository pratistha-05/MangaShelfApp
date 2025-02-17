package com.example.mangashelfapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.ui.components.UiState
import com.example.mangashelfapp.ui.viewmodel.MangaDetailViewModel

@Composable
fun MangaDetailScreen(
  mangaId: String?,
  viewModel: MangaDetailViewModel = hiltViewModel()
) {
  LaunchedEffect(mangaId) {
    mangaId?.let { viewModel.fetchMangaById(it) }
  }

  val uiState by viewModel.mangaDetailState.collectAsState()

  when (uiState) {
    is UiState.Loading -> Text("Loading...")
    is UiState.Success -> {
      MangaDetailLayout((uiState as UiState.Success).mangas.first())
    }
    is UiState.Error -> Text("Error: ${(uiState as UiState.Error).message}")
  }
}


@Composable
fun MangaDetailLayout(item: Manga) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    AsyncImage(
      model = item.imageUrl,
      contentDescription = item.title,
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(12.dp)),
      contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.height(12.dp))

    item.title.let {
      Text(
        text = it, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text("Score: ${item.score}")
    Text("Popularity: ${item.popularity}")
    Text("Published: ${item.year}")
  }
}
