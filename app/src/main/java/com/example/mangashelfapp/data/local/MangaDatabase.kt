package com.example.mangashelfapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MangaEntity::class], version = 3)
abstract class MangaDatabase : RoomDatabase() {
  abstract fun mangaDao(): MangaDao
}