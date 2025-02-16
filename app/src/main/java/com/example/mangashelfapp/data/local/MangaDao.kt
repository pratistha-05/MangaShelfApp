package com.example.mangashelfapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
  @Query("SELECT * FROM manga_table")
  fun getAllMangas(): Flow<List<MangaEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(mangas: List<MangaEntity>)
}
