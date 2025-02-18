package com.example.mangashelfapp.data.source

import com.example.mangashelfapp.data.model.Manga
import com.example.mangashelfapp.util.toYear

data class MangaDto(
  val id: String,
  val image: String?,
  val score: Double,
  val popularity: Int,
  val title: String,
  val publishedChapterDate: Long,
  val isFavourite:Boolean
)

fun MangaDto.toManga() = Manga(
  id = id,
  title = title,
  imageUrl = image,
  score = score,
  popularity = popularity,
  year = publishedChapterDate.toYear(),
  isFavourite = isFavourite

)