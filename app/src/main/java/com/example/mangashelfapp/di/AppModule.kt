package com.example.mangashelfapp.di

import android.app.Application
import androidx.room.Room
import com.example.mangashelfapp.data.local.MangaDao
import com.example.mangashelfapp.data.local.MangaDatabase
import com.example.mangashelfapp.data.source.ApiService
import com.example.mangashelfapp.data.repository.MangaRepository
import com.example.mangashelfapp.domain.SortMangasUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideMangaDatabase(app: Application): MangaDatabase {
    return Room.databaseBuilder(
      app,
      MangaDatabase::class.java,
      "manga_db"
    )
      .fallbackToDestructiveMigration()
      .build()
  }

  @Provides
  @Singleton
  fun provideMangaDao(db: MangaDatabase) = db.mangaDao()

  @Provides
  @Singleton
  fun provideMangaRepository(apiService: ApiService, dao: MangaDao): MangaRepository {
    return MangaRepository(apiService, dao)
  }

  @Provides
  @Singleton
  fun provideSortMangasUseCase(): SortMangasUseCase {
    return SortMangasUseCase()
  }
}
