package com.example.mangashelfapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangashelfapp.data.repository.MangaRepository
import com.example.mangashelfapp.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
  private val repository: MangaRepository
) : ViewModel() {
  private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
  val uiState: StateFlow<UiState> = _uiState.asStateFlow()

  init {
    fetchMangas()
  }

  fun fetchMangas() {
    viewModelScope.launch {
      _uiState.value = UiState.Loading
      repository.mangas.collect { localMangas ->
        _uiState.value = UiState.Success(localMangas)
      }
    }
    viewModelScope.launch {
      val result = repository.fetchMangaList()
      result.onFailure { e ->
        _uiState.value = UiState.Error(e.message ?: "Unknown error")
      }
    }
  }
}
