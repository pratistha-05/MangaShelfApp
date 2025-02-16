package com.example.mangashelfapp.data.source

import com.example.mangashelfapp.data.model.Manga
import retrofit2.http.GET

interface ApiService {
  @GET("b/KEJO")
  suspend fun getMangaList(): List<Manga>
}