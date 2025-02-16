package com.example.mangashelfapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MangaEntity::class], version = 4)
abstract class MangaDatabase : RoomDatabase() {
  abstract fun mangaDao(): MangaDao
}