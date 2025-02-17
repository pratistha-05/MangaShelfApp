package com.example.mangashelfapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangashelfapp.data.repository.MangaRepository
import com.example.mangashelfapp.ui.components.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
  private val repository: MangaRepository
) : ViewModel() {

  private val _mangaDetailState = MutableStateFlow<UiState>(UiState.Loading)
  val mangaDetailState: StateFlow<UiState> = _mangaDetailState.asStateFlow()

  fun fetchMangaById(mangaId: String) {
    viewModelScope.launch {
      repository.mangas.collect { localMangas ->
        val manga = localMangas.find { it.id == mangaId }
        if (manga != null) {
          _mangaDetailState.value = UiState.Success(listOf(manga))
        } else {
          _mangaDetailState.value = UiState.Error("Manga not found")
        }
      }
    }
  }
}

