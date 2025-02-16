package com.example.mangashelfapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mangashelfapp.data.model.Manga
import java.util.Calendar

@Entity(tableName = "manga_table")
data class MangaEntity(
  @PrimaryKey val id: String,
  val title: String,
  val imageUrl: String?,
  val score: Double,
  val popularity: Int,
  val year: Int
)

fun MangaEntity.toManga() = Manga(
  id = id,
  title = title,
  imageUrl = imageUrl,
  score = score,
  popularity = popularity,
  year = year
)

fun Manga.toEntity() = MangaEntity(
  id = id,
  title = title,
  imageUrl = imageUrl,
  score = score,
  popularity = popularity,
  year = year
)
