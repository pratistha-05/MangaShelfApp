package com.example.mangashelfapp.util

import com.example.mangashelfapp.data.model.Manga
import java.util.Calendar

fun Long.toYear(): Int {
  val calendar = Calendar.getInstance()
  calendar.timeInMillis = this * 1000
  return calendar.get(Calendar.YEAR)
}
