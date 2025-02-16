package com.example.mangashelfapp.domain.usecases

import com.example.mangashelfapp.data.local.MangaDao
import com.example.mangashelfapp.data.local.toEntity
import com.example.mangashelfapp.data.local.toManga
import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.data.source.ApiService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepository @Inject constructor(
  private val apiService: ApiService,
  private val mangaDao: MangaDao
) {
  val mangas = mangaDao.getAllMangas().map { entities -> entities.map { it.toManga() } }

  suspend fun fetchMangaList(): Result<List<Manga>> {
    return try {
      val mangas = apiService.getMangaList()
      mangaDao.insertAll(mangas.map { it.toEntity() })
      Result.success(mangas)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}