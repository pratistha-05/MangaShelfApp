package com.example.mangashelfapp.util

enum class SortOption {
  SCORE_ASC,
  SCORE_DESC,
  POPULARITY_ASC,
  POPULARITY_DESC,
  NONE;

  fun displayText(): String {
    return when (this) {
      SCORE_ASC -> "Score Ascending"
      SCORE_DESC -> "Score Descending"
      POPULARITY_ASC -> "Popularity Ascending"
      POPULARITY_DESC -> "Popularity Descending"
      NONE -> "Clear Sorting"
    }
  }
}
