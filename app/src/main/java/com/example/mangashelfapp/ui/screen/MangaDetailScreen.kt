package com.example.mangashelfapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.mangashelfapp.ui.viewmodel.MangaDetailViewModel

@Composable
fun MangaDetailScreen(
  mangaId: String?,
  viewModel: MangaDetailViewModel = hiltViewModel()
) {
  val manga by viewModel.mangaState.collectAsState()

  LaunchedEffect(mangaId) {
    if (mangaId != null) {
      viewModel.fetchMangaById(mangaId)
    }
  }

  manga?.let { item ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
      IconButton(
        onClick = { viewModel.toggleFavorite() }
      ) {
        Icon(
          imageVector = if (item.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
          contentDescription = "Toggle Favorite"
        )
      }

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

      Text(
        text = item.title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text("Score: ${item.score}")
      Text("Popularity: ${item.popularity}")
      Text("Published: ${item.year}")
    }
  } ?: run {
    Text(text = "Loading or Manga not found")
  }
}
