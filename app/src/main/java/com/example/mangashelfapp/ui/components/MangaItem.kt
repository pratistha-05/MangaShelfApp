package com.example.mangashelfapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mangashelfapp.data.model.Manga

@Composable
fun MangaItem(manga: Manga) {
  Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
    AsyncImage(
      model = manga.imageUrl,
      contentDescription = null,
      modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column() {
      Text(text = manga.title, fontWeight = FontWeight.Bold)
      Text(text = "Score: ${manga.score}")
      Text(text = "Popularity: ${manga.popularity}")
      Text(text = "Year: ${manga.year}")
    }
  }
}