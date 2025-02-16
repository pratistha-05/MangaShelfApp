package com.example.mangashelfapp.domain

import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.util.SortOption

class SortMangasUseCase {
  operator fun invoke(mangas: List<Manga>, sortOption: SortOption): List<Manga> {
    return when (sortOption) {
      SortOption.SCORE_ASC -> mangas.sortedBy { it.score }
      SortOption.SCORE_DESC -> mangas.sortedByDescending { it.score }
      SortOption.POPULARITY_ASC -> mangas.sortedBy { it.popularity }
      SortOption.POPULARITY_DESC -> mangas.sortedByDescending { it.popularity }
      SortOption.NONE -> mangas
    }
  }
}
