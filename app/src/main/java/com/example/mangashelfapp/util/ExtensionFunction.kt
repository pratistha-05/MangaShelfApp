package com.example.mangashelfapp.util

import java.util.Calendar

fun Long.toYear(): Int {
  val calendar = Calendar.getInstance()
  calendar.timeInMillis = this * 1000 // Convert seconds to milliseconds
  return calendar.get(Calendar.YEAR)
}