package com.example.mangashelfapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mangashelfapp.data.model.Manga

@Composable
fun MangaItem(manga: Manga, onClick: (Manga) -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 8.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(12.dp)
      )
      .clickable {
        onClick(manga)
                 },
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      AsyncImage(
        model = manga.imageUrl,
        contentDescription = null,
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.width(12.dp))

      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = manga.title,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Score: ${manga.score}",
          style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
        )

        Text(
          text = "Popularity: ${manga.popularity}",
          style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.secondary)
        )

        Text(
          text = "Year: ${manga.year}",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    }
  }
}
