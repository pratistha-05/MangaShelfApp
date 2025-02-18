package com.example.mangashelfapp.data.model


data class Manga(
  val id: String,
  val title: String,
  val imageUrl: String?,
  val score: Double,
  val popularity: Int,
  val year: Int,
  val isFavourite:Boolean
)
