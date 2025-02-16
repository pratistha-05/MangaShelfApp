package com.example.mangashelfapp.data.source

import retrofit2.http.GET

interface ApiService {
  @GET("b/KEJO")
  suspend fun getMangaList(): List<MangaDto>
}