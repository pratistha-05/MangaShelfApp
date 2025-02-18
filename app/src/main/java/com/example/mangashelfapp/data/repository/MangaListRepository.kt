package com.example.mangashelfapp.data.repository

import androidx.compose.runtime.*
import com.example.mangashelfapp.data.local.MangaDao
import com.example.mangashelfapp.data.local.MangaEntity
import com.example.mangashelfapp.data.local.toEntity
import com.example.mangashelfapp.data.local.toManga
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.data.source.ApiService
import com.example.mangashelfapp.data.source.toManga
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepository @Inject constructor(
  private val apiService: ApiService,
  private val mangaDao: MangaDao
) {


  val mangas: Flow<List<Manga>> = mangaDao.getAllMangas()
    .map { entities -> entities.map { it.toManga() } }

  suspend fun fetchMangaList(): Result<List<Manga>> {
    return try {
      val mangaDtos = apiService.getMangaList()
      val mangas = mangaDtos.map { it.toManga() }

      mangaDao.insertAll(mangas.map { it.toEntity() })

      Result.success(mangas)
    } catch (e: Exception) {
      val cachedMangas = mangaDao.getAllMangasOnce().map { it.toManga() }
      if (cachedMangas.isNotEmpty()) {
        Result.success(cachedMangas)
      } else {
        Result.failure(e)
      }
    }
  }

  suspend fun toggleFavorite(manga: MangaEntity) {
    mangaDao.updateManga(manga.copy(isFavorite = !manga.isFavorite))
  }


}
