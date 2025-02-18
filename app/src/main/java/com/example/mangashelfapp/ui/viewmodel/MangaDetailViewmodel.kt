package com.example.mangashelfapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangashelfapp.data.local.toEntity
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.data.repository.MangaRepository
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

  private val _mangaState = MutableStateFlow<Manga?>(null)
  val mangaState: StateFlow<Manga?> = _mangaState.asStateFlow()

  fun fetchMangaById(mangaId: String) {
    viewModelScope.launch {
      repository.mangas.collect { localMangas ->
        val manga = localMangas.find { it.id == mangaId }
        _mangaState.value = manga
      }
    }
  }

  fun toggleFavorite() {
    _mangaState.value?.let { manga ->
      val updatedManga = manga.copy(isFavourite = !manga.isFavourite)
      _mangaState.value = updatedManga
      viewModelScope.launch {
        repository.toggleFavorite(updatedManga.toEntity())
      }
    }
  }

}

