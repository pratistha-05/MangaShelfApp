package com.example.mangashelfapp.ui

import com.example.mangashelfapp.data.model.Manga

sealed class UiState {
  object Loading : UiState()
  data class Success(val mangas: List<Manga>) : UiState()
  data class Error(val message: String) : UiState()
}