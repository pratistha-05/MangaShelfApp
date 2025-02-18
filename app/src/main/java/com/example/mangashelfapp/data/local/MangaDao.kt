package com.example.mangashelfapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
  @Query("SELECT * FROM manga_table")
  fun getAllMangas(): Flow<List<MangaEntity>>

  @Query("SELECT * FROM manga_table")
  suspend fun getAllMangasOnce(): List<MangaEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(mangas: List<MangaEntity>)

  @Update
  suspend fun updateManga(manga: MangaEntity)

  @Query("SELECT * FROM manga_table WHERE isFavorite = 1")
  fun getFavoriteMangas(): Flow<List<MangaEntity>>
}
